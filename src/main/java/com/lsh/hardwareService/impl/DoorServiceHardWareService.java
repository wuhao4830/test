package com.lsh.hardwareService.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lsh.data.ServiceData;
import com.lsh.enums.HardWareType;
import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.model.Hardware;
import net.sf.ezmorph.bean.MorphDynaBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhao on 2018/6/1.
 */
public class DoorServiceHardWareService implements HardWareBaseService {

    public Hardware hardware;

    private static Integer type = HardWareType.DOOR.getValue();

    private static final Logger logger = LoggerFactory.getLogger(DoorServiceHardWareService.class);

    //在构造函数执行前执行，这个有坑，有可能没机会执行，这个需要关注下
    static {
        ServiceData.register(type,DoorServiceHardWareService.class);
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public int getStatus() {
//        //获取硬件状态
//        String command = "AT";
//        String rstString = NsHeadClient.jsonCall(hardware.getIp(),hardware.getPort(),command);
        return 0;
    }

    public boolean isAilve() {
//        //获取硬件状态
//        String command = "AT";
//        String rstString = NsHeadClient.jsonCall(hardware.getIp(), hardware.getPort(), command);
//        if(rstString!=null && rstString.equals("OK")){
//            return true;
//        }
        return true;
    }

    public Map openDoor(MorphDynaBean morphDynaBean) {
        logger.info("run open door");
        return new HashMap();
    }

    public Map closeDoor(MorphDynaBean morphDynaBean) {
        logger.info("run close door");
        return new HashMap();
    }
    public Object operator(String command,Object cmdParams) throws Exception{
        MorphDynaBean beanParams = null;
        if(StringUtils.isNotEmpty(cmdParams.toString())){
            //不是空的，那就是MorphDynaBean类型的
            beanParams = (MorphDynaBean)cmdParams;
        }
        Object result = null;
        try {
            if(beanParams==null){
                Method method = this.getClass().getMethod(command);
                result = method.invoke(this);
            }else {
                Method method = this.getClass().getMethod(command,MorphDynaBean.class);
                result = method.invoke(this, beanParams);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        logger.info("done operator");
        return result;
    }

    public void test(){
        System.out.print("111111111");
    }

    public static void main(String args[])throws Exception{
        HardWareBaseService doorServiceHardWareService = new DoorServiceHardWareService();
        doorServiceHardWareService.operator("test",null);

    }
}
