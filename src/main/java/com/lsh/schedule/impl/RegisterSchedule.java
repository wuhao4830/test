package com.lsh.schedule.impl;

import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.register.HardwareRegisterService;
import com.lsh.schedule.BaseSchedule;
import com.lsh.utils.ClassUtils;
import com.lsh.utils.PropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuhao on 2018/6/4.
 */
public class RegisterSchedule implements BaseSchedule{

    private static final Logger logger = LoggerFactory.getLogger(RegisterSchedule.class);

    public void start() throws Exception {


        //本地注册
        //serviceRegister();


        Timer timer = new Timer();
        try {
            final Long refreshCapacity = Long.valueOf(PropsUtils.get("client.register.refreshCapacity"));
            logger.info("start register timer refreshCapacity:" + refreshCapacity);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        HardwareRegisterService hardwareRegisterService = HardwareRegisterService.getInstance();
                        hardwareRegisterService.init();
                    } catch (Throwable throwable) {
                        logger.error(throwable.getMessage(), throwable);
                    }

                }
            }, 0, refreshCapacity);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    public void serviceRegister(){
        try {

            List<Class> classes = ClassUtils.getAllClassByInterface(HardWareBaseService.class);
            for (Class baseClass : classes) {
                logger.info(baseClass.getName());
                HardWareBaseService baseService = (HardWareBaseService) baseClass.newInstance();
                baseService.register();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage()==null ? "" : e.getMessage());
        }
    }
}
