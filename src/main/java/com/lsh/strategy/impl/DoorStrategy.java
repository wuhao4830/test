package com.lsh.strategy.impl;

import com.lsh.data.ServiceData;
import com.lsh.enums.HardWareType;
import com.lsh.model.Hardware;
import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.hardwareService.impl.DoorServiceHardWareService;
import com.lsh.register.HardwareRegisterService;
import com.lsh.strategy.BaseStrategy;
import com.lsh.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuhao on 2018/6/4.
 */
public class DoorStrategy implements BaseStrategy {

    private static final Logger logger = LoggerFactory.getLogger(DoorStrategy.class);


    public DoorStrategy() {
        logger.info("init DoorStrategy");
    }

    public List<HardWareBaseService> getHardWare() {
//        //TODO 暂时写死IP，端口，和Mac
//        String macStrr = "4E:17:11:15:02:18";
//        Hardware hardware = new Hardware();
//        IdGenerator idGenerator = IdGenerator.getInstance();
//        String macId = idGenerator.genMacId(macStrr);
//        hardware.setMacId(macId);
//        hardware.setIp("192.168.149.199");
//        hardware.setMacStrr(macStrr);
//        hardware.setPort(12345);
//        hardware.setType(HardWareType.DOOR.getValue());
//        List<HardWareBaseService> baseServices = new ArrayList<HardWareBaseService>();
//        DoorServiceHardWareService doorService = new DoorServiceHardWareService();
//        doorService.setHardware(hardware);
//        baseServices.add(doorService);
//        return baseServices ;

        String macStrr = "4E:17:11:15:02:18";
        Hardware hardware = new Hardware();
        IdGenerator idGenerator = IdGenerator.getInstance();
        String macId = idGenerator.genMacId(macStrr);
        hardware.setMacId(macId);
        hardware.setIp("192.168.149.199");
        hardware.setMacStrr(macStrr);
        hardware.setPort(12345);
        hardware.setType(HardWareType.DOOR.getValue());
        List<HardWareBaseService> baseServices = new ArrayList<HardWareBaseService>();
        DoorServiceHardWareService doorService = new DoorServiceHardWareService();
        doorService.setHardware(hardware);
        baseServices.add(doorService);
        return baseServices ;
    }
    public Class getClass11() {
        return this.getClass();
    }
    public static void main(String args[]) {
        DoorStrategy doorStrategy = new DoorStrategy();
        doorStrategy.getHardWare();
        System.out.println(doorStrategy.getClass11());
        System.out.println(ServiceData.getClassByType(HardWareType.DOOR.getValue()));
    }
}
