package com.lsh.monitor;

import com.alibaba.fastjson.JSON;
import com.lsh.control.CentralControllerService;
import com.lsh.data.HardWareData;
import com.lsh.enums.HardWareType;
import com.lsh.enums.ServiceOperatorType;
import com.lsh.enums.SocketType;
import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.model.Hardware;
import com.lsh.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhao on 2018/5/31.
 */
public class HardwareMonitorService {

    private static final Logger logger = LoggerFactory.getLogger(HardwareMonitorService.class);


    private HardwareMonitorService() {

    }

    //单粒模式内部类
    private static class  HardwareMonitorServiceSingletonFactory {
        private static HardwareMonitorService instance = new HardwareMonitorService();
    }


    public static HardwareMonitorService getInstance() {
        return HardwareMonitorServiceSingletonFactory.instance;
    }



    public void getHardWareStatus(){
        Map<String,HardWareBaseService> mac2StrategyMap = HardWareData.getMac2StrategyMap();
        IdGenerator idGenerator = IdGenerator.getInstance();
        logger.info("-------"+JSON.toJSONString(mac2StrategyMap));
        for (Map.Entry<String, HardWareBaseService> entry : mac2StrategyMap.entrySet()) {
            HardWareBaseService baseService = entry.getValue();
            Hardware hardware = baseService.getHardware();
            Integer status = baseService.isAilve();

            Map<String,Object> hardwareInfo = new HashMap<String, Object>();

            String messageId = idGenerator.genId();
            //（上报硬件状态给服务器）
            CentralControllerService centralControllerService = CentralControllerService.getInstance();
            hardwareInfo.put("macId", hardware.getMacId());
            hardwareInfo.put("status",status.toString());
            hardwareInfo.put("deviceType",hardware.getType());
            if (hardware.getType().compareTo(HardWareType.SETTLEMENT.getValue())==0){
                continue;
            }
            hardwareInfo.put("cmd",ServiceOperatorType.DOOR_STATUS.getValue());
            try {
                logger.info("send status:"+ JSON.toJSONString(hardware));
                centralControllerService.sendMessage(messageId,hardwareInfo, SocketType.CLIENT_REQUEST.getValue());
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage(),e);
            }

        }

    }
}
