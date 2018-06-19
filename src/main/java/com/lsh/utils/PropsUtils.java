package com.lsh.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wuhao on 2016/10/27.
 */
public class PropsUtils {


    private static final Properties props = new Properties();


    private static final Logger logger = LoggerFactory.getLogger(PropsUtils.class);



    static {
        try {
            props.load(PropsUtils.class.getClassLoader().getResourceAsStream("props/getway.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }


    public static String get(String key){
        return props.get(key).toString();
    }

}
