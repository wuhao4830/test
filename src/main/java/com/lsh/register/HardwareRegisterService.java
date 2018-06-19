package com.lsh.register;

import com.lsh.control.CentralControllerService;
import com.lsh.data.HardWareData;
import com.lsh.data.SocketMessageData;
import com.lsh.enums.OperatorType;
import com.lsh.enums.ServiceOperatorType;
import com.lsh.enums.SocketType;
import com.lsh.model.Hardware;
import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.model.SocketMessage;
import com.lsh.strategy.BaseStrategy;
import com.lsh.utils.ClassUtils;
import com.lsh.utils.IdGenerator;
import com.lsh.utils.PropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by wuhao on 2018/5/31.
 */
public class HardwareRegisterService {


    private static final Logger logger = LoggerFactory.getLogger(HardwareRegisterService.class);


    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private HardwareRegisterService () {

    }

    //单粒模式内部类
    private static class  HardwareRegisterServiceSingletonFactory {
        private static HardwareRegisterService instance = new HardwareRegisterService();
    }

    public static HardwareRegisterService getInstance() {
        return HardwareRegisterServiceSingletonFactory.instance;
    }

    public void register(HardWareBaseService baseService){
        Hardware hardware = baseService.getHardware();
        IdGenerator idGenerator = IdGenerator.getInstance();

        Map<String,Object> hardwareInfo = new HashMap<String, Object>();
        if (HardWareData.saveData(baseService)) {
            String messageId = idGenerator.genId();
            //这个message信息要存起来.
            SocketMessage socketMessage = new SocketMessage(SocketType.CLIENT_REQUEST.getValue(),hardware,messageId, OperatorType.REGISTER.getValue());
            SocketMessageData.saveMessage(socketMessage);
            //没有注册过（上报给服务器）
            CentralControllerService centralControllerService = CentralControllerService.getInstance();
            //将网关地址，Ip，端口全部上报给服务器
            hardwareInfo.put("macId", hardware.getMacId());
            hardwareInfo.put("deviceType",hardware.getType());
            hardwareInfo.put("cmd",ServiceOperatorType.REGISTER.getValue());
            try {
                centralControllerService.sendMessage(messageId,hardwareInfo, SocketType.CLIENT_REQUEST.getValue());
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                logger.error("register fail");
                //上报报错了，需要移除回滚
                HardWareData.removeData(baseService);
                SocketMessageData.removeMessage(socketMessage);
            }
        }
    }

    public void init() {
        try {
            List<Class> classes = ClassUtils.getAllClassByInterface(BaseStrategy.class);
            for(Class baseClass : classes){
                BaseStrategy baseStrategy = (BaseStrategy)baseClass.newInstance();
                this.executor.submit(new LogTask(this, baseStrategy));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    private static class LogTask implements Callable<Boolean> {

        private  final BaseStrategy baseStrategy;

        private final HardwareRegisterService hardwareRegisterService;

        public LogTask(final  HardwareRegisterService hardwareRegisterService,final BaseStrategy baseStrategy) {
            this.baseStrategy = baseStrategy;
            this.hardwareRegisterService = hardwareRegisterService;
        }


        public Boolean call() throws Exception {
            try {

                List<HardWareBaseService> baseServices = baseStrategy.getHardWare();
                for (HardWareBaseService baseService : baseServices) {
                    hardwareRegisterService.register(baseService);
                }
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                return false;
            }
            return true;
        }
    }

    public static void main(String args[]) throws Exception{
        HardwareRegisterService hardwareRegisterService = new HardwareRegisterService();
        hardwareRegisterService.init();
    }
}
