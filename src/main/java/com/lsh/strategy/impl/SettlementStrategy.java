package com.lsh.strategy.impl;

import com.lsh.data.ServiceData;
import com.lsh.enums.HardWareType;
import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.hardwareService.impl.SettlementServiceHardWareService;
import com.lsh.model.Hardware;
import com.lsh.strategy.BaseStrategy;
import com.lsh.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhao on 2018/6/4.
 */
public class SettlementStrategy implements BaseStrategy {

    public SettlementStrategy() {
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

        //String macStrr = "4E:17:11:15:02:18";
        Hardware hardware = new Hardware();
        IdGenerator idGenerator = IdGenerator.getInstance();
        String macId = "110011";
        hardware.setMacId(macId);
        hardware.setIp("127.0.0.1");
        hardware.setMacStrr(macId);
        hardware.setPort(0);
        hardware.setType(HardWareType.SETTLEMENT.getValue());
        List<HardWareBaseService> baseServices = new ArrayList<HardWareBaseService>();
        SettlementServiceHardWareService settlementServiceHardWareService = SettlementServiceHardWareService.getInstance();
        settlementServiceHardWareService.setHardware(hardware);
        baseServices.add(settlementServiceHardWareService);
        return baseServices ;
    }
    public Class getClass11() {
        return this.getClass();
    }
    public static void main(String args[]) {
        SettlementStrategy doorStrategy = new SettlementStrategy();
        doorStrategy.getHardWare();
        System.out.println(doorStrategy.getClass11());
        System.out.println(ServiceData.getClassByType(HardWareType.DOOR.getValue()));
    }
}
