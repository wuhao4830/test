package com.lsh.data;

import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhao on 2018/6/13.
 */
public class HardWareData {
    //硬件注册数据(需要注册硬件的获取服务)
    private static Map<String,HardWareBaseService> mac2StrategyMap = new HashMap<String, HardWareBaseService>();
    private static final Logger logger = LoggerFactory.getLogger(HardWareData.class);


    public static Map<String, HardWareBaseService> getMac2StrategyMap() {
        return mac2StrategyMap;
    }

    public static boolean saveData(HardWareBaseService baseService) {
        Hardware hardware = baseService.getHardware();
        synchronized (mac2StrategyMap) {
            if (!mac2StrategyMap.containsKey(hardware.getMacId())) {
                //没有组册过（1.放入到本地注册库中）
                mac2StrategyMap.put(hardware.getMacId(), baseService);
                return true;
            }
        }
        return false;
    }

    public static boolean removeData(HardWareBaseService baseService) {
        Hardware hardware = baseService.getHardware();
        synchronized (mac2StrategyMap) {
            if (mac2StrategyMap.containsKey(hardware.getMacId())) {
                //注册过
                logger.info("remove HardWareData :"+ hardware);
                mac2StrategyMap.remove(hardware.getMacId());
                return true;
            }
        }
        return false;
    }

}
