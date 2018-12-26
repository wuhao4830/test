package com.lsh.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhao on 2018/6/13.
 */
public class ServiceData {
    //硬件注册数据(需要注册硬件的获取服务)
    private static Map<String,Class> service2ClassMap = new HashMap<String, Class>();


    public static boolean register(String type,Class service) {
        synchronized (service2ClassMap) {
            if (!service2ClassMap.containsKey(type)) {
                //没有存储过（1.放入到本地注册库中）
                service2ClassMap.put(type, service);
                return true;
            }
        }
        return false;
    }

    public static Class getClassByType(String hardWareType) {
        synchronized (service2ClassMap) {
            if (service2ClassMap.containsKey(hardWareType)) {
                //注册过
                return service2ClassMap.get(hardWareType);
            }
        }
        return null;
    }

}
