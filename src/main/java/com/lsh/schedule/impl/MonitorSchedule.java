package com.lsh.schedule.impl;

import com.lsh.monitor.HardwareMonitorService;
import com.lsh.register.HardwareRegisterService;
import com.lsh.schedule.BaseSchedule;
import com.lsh.utils.PropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuhao on 2018/6/4.
 */
public class MonitorSchedule implements BaseSchedule{

    private static final Logger logger = LoggerFactory.getLogger(MonitorSchedule.class);

    public void start() throws Exception {
        try {

            final Long refreshCapacity = Long.valueOf(PropsUtils.get("client.monitor.refreshCapacity"));
            Timer timer = new Timer();
            logger.info("start monitor timer refreshCapacity:" + refreshCapacity);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        logger.info("run hardwareMonitorService");
                        HardwareMonitorService hardwareMonitorService = HardwareMonitorService.getInstance();
                        hardwareMonitorService.getHardWareStatus();
                    } catch (Throwable throwable) {
                        logger.error(throwable.getMessage(), throwable);
                    }

                }
            }, 0, refreshCapacity);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
