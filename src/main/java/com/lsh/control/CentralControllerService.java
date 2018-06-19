package com.lsh.control;

import com.alibaba.fastjson.JSON;
import com.lsh.base.common.utils.BeanMapTransUtils;
import com.lsh.data.HardWareData;
import com.lsh.data.SocketMessageData;
import com.lsh.enums.OperatorType;
import com.lsh.enums.SocketType;
import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.model.OperatorBean;
import com.lsh.model.SocketBean;
import com.lsh.model.SocketHead;
import com.lsh.model.SocketMessage;
import com.lsh.utils.JsonUtils;
import com.lsh.utils.NsHeadClient;
import net.sf.ezmorph.bean.MorphDynaBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wuhao on 2018/6/5.
 */
public class CentralControllerService {


    private static final Logger logger = LoggerFactory.getLogger(CentralControllerService.class);

    private ReentrantLock lock = new ReentrantLock(true);

    private CentralControllerService () {

    }

    //单粒模式内部类
    private static class  CentralControllerServiceSingletonFactory {
        private static CentralControllerService instance = new CentralControllerService();
    }


    public static CentralControllerService getInstance() {
        return CentralControllerServiceSingletonFactory.instance;
    }

    public  void getMessage() throws Exception{
        NsHeadClient  nsHeadClient= NsHeadClient.getInstance();
        while (true) {
            //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            //跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。
            logger.info("run message listener");
            Thread.sleep(1000);
            try {
                if(lock.tryLock()) {
                    SocketBean socketBean = nsHeadClient.getSocketBean();
                    if (socketBean == null) {
                        continue;
                    }
                    //这个根据type来取是否是返回结果
                    SocketHead head = BeanMapTransUtils.map2Bean(socketBean.getHead(), SocketHead.class);
                    if (head.getType() == SocketType.SERVICE_RESPONSE.getValue()) {
                        //是服务器返回的数据
                        operatorReturnMessage(head, socketBean.getBody());
                        continue;
                    }

                    //根据传过来的macId找到对应的服务
                    //这个地方特殊处理cmdParams这个字段
                    Map<String,Object> body = socketBean.getBody();

                    OperatorBean operatorBean = BeanMapTransUtils.map2Bean(body, OperatorBean.class);
                    HardWareBaseService hardWareBaseService = HardWareData.getMac2StrategyMap().get(operatorBean.getMacId());
                    if (hardWareBaseService == null) {
                        logger.error("have not service to run ");
                        continue;
                    }
                    Object result = hardWareBaseService.operator(operatorBean.getCmd(), operatorBean.getCmdParams());
                    if (result != null) {
                        //TODO 这个有一个风险点，如果发送失败了怎么办？
                        this.sendMessage(head.getMessageId(),result,SocketType.CLIENT_RESPONSE.getValue());
                    }
                }

            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }finally {
                lock.unlock();
            }
        }

    }


    public  void sendMessage(String messageId,Object message,int type) throws Exception{

        logger.info("sendMessage:" + JSON.toJSONString(message));
        NsHeadClient nsHeadClient = NsHeadClient.getInstance();
        nsHeadClient.jsonCall(JsonUtils.SUCCESS(message,type,messageId));
    }

    public  void operatorReturnMessage(SocketHead socketHead,Map body) throws Exception{
        logger.info("return message value:"+body);
        SocketMessage socketMessage = SocketMessageData.getMessage(socketHead.getMessage());
        if(socketMessage!=null){
            //TODO 是返回的数据，这个地方要做处理,根据业务定

            if(socketMessage.getType()== OperatorType.REGISTER.getValue()){
                //是注册返回的信息
                if(socketHead.getStatus()==200){
                    logger.info("register success :"+ socketMessage);
                    //注册成功，移除message信息
                    SocketMessageData.removeMessage(socketMessage);
                }
            }
        }

    }

    public static void main(String args[]) throws Exception{
        CentralControllerService centralControllerService = CentralControllerService.getInstance();
        centralControllerService.getMessage();
    }
}
