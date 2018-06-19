package com.lsh;

import com.lsh.control.CentralControllerService;
import com.lsh.schedule.BaseSchedule;
import com.lsh.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception{
        //获取到调度器的实例

        List<Class> classes =  ClassUtils.getAllClassByInterface(BaseSchedule.class);
        for(Class baseClass : classes){
            BaseSchedule baseSchedule = (BaseSchedule)baseClass.newInstance();
            logger.info("run baseSchedule" + baseSchedule.getClass().getName());
            baseSchedule.start();
        }

        //启动Central
        CentralControllerService centralControllerService = CentralControllerService.getInstance();
        centralControllerService.getMessage();

        logger.info("Main run success");
    }
}
