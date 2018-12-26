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

    private static String type = HardWareType.DOOR.getValue();

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

    public void register() {
        ServiceData.register(type, this.getClass());
    }

    public Map getStatus() {
//        //获取硬件状态
//        String command = "AT";
//        String rstString = NsHeadClient.jsonCall(hardware.getIp(),hardware.getPort(),command);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("status","0");
        result.put("cmd","2");
        return result;
    }

    public int isAilve() {
//        //获取硬件状态
//        String command = "AT";
//        String rstString = NsHeadClient.jsonCall(hardware.getIp(), hardware.getPort(), command);
//        if(rstString!=null && rstString.equals("OK")){
//            return true;
//        }
        return 0;
    }

    public Map openDoor() {
        logger.info("run open door");
        return new HashMap();
    }

    public Map closeDoor() {
        logger.info("run close door");
        return new HashMap();
    }
    public Object operator(String command,Object cmdParams) throws Exception{
        Map beanParams = null;
        if(StringUtils.isNotEmpty(cmdParams.toString())){
            //不是空的，那就是MorphDynaBean类型的
            beanParams = (Map)cmdParams;
        }
        Object result = null;

        if(beanParams==null || beanParams.isEmpty()){
            Method method = this.getClass().getMethod(command);
            result = method.invoke(this);
        }else {
            Method method = this.getClass().getMethod(command,Map.class);
            result = method.invoke(this, beanParams);
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
