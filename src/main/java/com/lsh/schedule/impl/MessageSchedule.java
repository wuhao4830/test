package com.lsh.schedule.impl;

import com.lsh.control.CentralControllerService;
import com.lsh.schedule.BaseSchedule;
import com.lsh.utils.PropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuhao on 2018/6/4.
 */
public class MessageSchedule implements BaseSchedule{

    private static final Logger logger = LoggerFactory.getLogger(MessageSchedule.class);

    public void start() throws Exception {
        Timer timer = new Timer();
        try {

            final Long refreshCapacity = Long.valueOf(PropsUtils.get("client.message.refreshCapacity"));
            logger.info("start message timer refreshCapacity:" + refreshCapacity);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        CentralControllerService centralControllerService = CentralControllerService.getInstance();
                        centralControllerService.getMessage();

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
