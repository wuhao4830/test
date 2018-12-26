package com.lsh.utils;

import com.relops.snowflake.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ID生成器
 * Created by wuHao on 16/11/5.
 */
public class IdGenerator {

    private static Map<String,String> macStrr2macIdMap = new HashMap<String, String>();
    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);


    private IdGenerator () {
        snowflake = new Snowflake(Integer.valueOf(PropsUtils.get("prefix")));
    }

    //单粒模式内部类
    private static class  IdGeneratorSingletonFactory {
        private static IdGenerator instance = new IdGenerator();
    }

    public static IdGenerator getInstance() {
        return IdGeneratorSingletonFactory.instance;
    }


    private  Snowflake snowflake;

    public static String uuid() {
        char[] chars = UUID.randomUUID().toString().toCharArray();
        char[] uuid = new char[32];
        for (int i = 0, j = 0; i < 36; i++) {
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                continue;
            }
            uuid[j++] = chars[i];
        }
        return new String(uuid);
    }

    public String genId() {
        String id =  snowflake.next() + "";
        //String id =  "6413335619551891456";
        return id;
    }
    public Long genLongId() {
        return snowflake.next();
    }

    public static String genIdTest() {
        Snowflake snowflakeTest = new Snowflake(1);
        return snowflakeTest.next() + "";
    }

    public String genCode(String prefix) {
        String code = new StringBuilder(prefix).append(genId()).toString();
        return code;
    }

    public String genMacId(String macStrr) {
        return macStrr.replace(":","");
    }
}
