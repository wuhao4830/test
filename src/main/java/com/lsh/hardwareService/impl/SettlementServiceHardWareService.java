package com.lsh.hardwareService.impl;

import com.lsh.data.HardWareData;
import com.lsh.data.ServiceData;
import com.lsh.enums.HardWareType;
import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.model.Hardware;
import com.lsh.monitor.HardwareMonitorService;
import com.lsh.utils.PropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuhao on 2018/6/1.
 */
public class SettlementServiceHardWareService implements HardWareBaseService {

    public Hardware hardware;


    private static String tag = "SettlementServiceHardWareService";

    private static final Logger logger = LoggerFactory.getLogger(DoorServiceHardWareService.class);



    //心跳的应用迁移到结算台上面，结算台是只有一个实例的，可以支持单粒.
    Timer timer = new Timer();

    private static String type = HardWareType.SETTLEMENT.getValue();

    public void register() {
        ServiceData.register(type, this.getClass());
    }


    private SettlementServiceHardWareService(){
        this.start();
    }


    //单粒模式内部类
    private static class  SettlementServiceHardWareServiceSingletonFactory {
        private static SettlementServiceHardWareService instance = new SettlementServiceHardWareService();
    }


    public static SettlementServiceHardWareService getInstance() {
        return SettlementServiceHardWareServiceSingletonFactory.instance;
    }


    public Hardware getHardware() {
        return hardware;
    }


    public Map addHardWare(Map params) throws Exception{
        Hardware newHardWare = Hardware.map2Bean(params);
        HardWareBaseService hardWareBaseService = HardWareData.getMac2StrategyMap().get(newHardWare.getMacId());
        if(hardWareBaseService==null){
            //新设备，需要添加
            Class hareWareServiceClass = ServiceData.getClassByType(newHardWare.getType());
            hardWareBaseService = (HardWareBaseService)hareWareServiceClass.newInstance();
            hardWareBaseService.setHardware(newHardWare);
            HardWareData.saveData(hardWareBaseService);
        }

        return new HashMap();
    }

    public Map updateMonitorRefreshCapacity(Map params) {
        logger.info(tag, "run updateMonitorRefreshCapacity");
        Long refreshCapacity =  Long.valueOf(params.get("refreshCapacity").toString());
        setRefreshTime(refreshCapacity);
        return new HashMap();
    }


    public Map upSettlement(Map params) {
        return new HashMap();
    }

    public Map upSettlement() {
       return upSettlement(new HashMap());
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }
    public Map getStatus() {
//        //获取硬件状态
//        String command = "AT";
//        String rstString = NsHeadClient.jsonCall(hardware.getIp(),hardware.getPort(),command);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("status", "0");
        result.put("cmd", "2");
        return result;
    }

    public int isAilve() {
        boolean isALive = true;
        return isALive?1:0;
    }
    public Object operator(String command,Object cmdParams) throws Exception{
        Map beanParams = null;
        if(cmdParams!=null && !cmdParams.toString().equals("")){
            //不是空的，那就是Map类型的
            beanParams = (Map)cmdParams;
        }
        Class clazz = this.getClass();
        if(beanParams==null || beanParams.isEmpty()){
            Method method = clazz.getMethod(command);
            return method.invoke(this);
        }else {
            Method method = clazz.getMethod(command,Map.class);
            return method.invoke(this, beanParams);
        }
    }

    public void test(){
        System.out.print("111111111");
    }

    public static void main(String args[])throws Exception{
        HardWareBaseService doorServiceHardWareService = new SettlementServiceHardWareService();
        doorServiceHardWareService.operator("test", null);

    }


    public void start() {
        try {
            final Long refreshCapacity = Long.valueOf(PropsUtils.get("client.monitor.refreshCapacity"));
            timer.schedule(new MonitorTimerTask(), 0, refreshCapacity);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(tag,e.getMessage());
        }
    }
    public void setRefreshTime(Long refreshCapacity){
        timer.cancel();
        timer.schedule(new MonitorTimerTask(), 0, refreshCapacity);
    }

    public class MonitorTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                HardwareMonitorService hardwareMonitorService = HardwareMonitorService.getInstance();
                hardwareMonitorService.getHardWareStatus();
            } catch (Throwable throwable) {
                logger.error(tag,throwable.getMessage());
            }

        }
    }
}
