package com.lsh.utils;

import com.lsh.base.common.json.JsonMapper;
import com.lsh.base.common.utils.DateUtils;
import com.lsh.model.SocketBean;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.net.URLDecoder;
import java.util.*;

/**
 * Created by wuhao on 2018/6/5.
 */
public class JsonUtils {
    public static final Integer SUCCESS = Integer.valueOf(1);
    public static final Integer TOKEN_EXCEPTION = Integer.valueOf(5);
    private static JsonMapper jsonMapper = new JsonMapper();
    private static String gatewayId = PropsUtils.get("gatewayId");

    public JsonUtils() {
    }

    public static JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    public static String obj2Json(Object obj) {
        return jsonMapper.toJson(obj);
    }

    public static <T> T json2Obj(String json, Class<T> clazz) {
        return jsonMapper.fromJson(json, clazz);
    }

    public static String formatJson(String json) {
        return jsonMapper.formatJson(json);
    }

    public static String SUCCESS(String messageId,int type) {
        LinkedHashMap head = new LinkedHashMap();
        head.put("status", SUCCESS);
        head.put("type",type);
        head.put("gatewayId", gatewayId);
        head.put("message", "success.");
        head.put("messageId",messageId);
        head.put("timestamp", DateUtils.getCurrentSeconds());
        LinkedHashMap map = new LinkedHashMap();
        map.put("head", head);
        map.put("body", new HashMap<String,Object>());
        return obj2Json(map);
    }

    public static String SUCCESS(Object message,int type,String messageId) {
        LinkedHashMap head = new LinkedHashMap();
        head.put("status", SUCCESS);
        head.put("gatewayId", gatewayId);
        head.put("message", "success.");
        head.put("type",type);
        head.put("messageId", messageId);
        head.put("timestamp",  DateUtils.getCurrentSeconds());
        LinkedHashMap map = new LinkedHashMap();
        map.put("head", head);
        map.put("body", message);
        return obj2Json(map);
    }



    public static String TOKEN_ERROR(int type,String messageId) {
        LinkedHashMap head = new LinkedHashMap();
        head.put("status", TOKEN_EXCEPTION);
        head.put("messageId", messageId);
        head.put("gatewayId", SUCCESS);
        head.put("type", type);
        head.put("message", "system exception.");
        head.put("timestamp",  DateUtils.getCurrentSeconds());
        LinkedHashMap map = new LinkedHashMap();
        map.put("head", head);
        map.put("body", new HashMap<String,Object>());
        return obj2Json(map);
    }




    public static JsonConfig getDecodeJSONConfig(){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(String.class, new JsonValueProcessor() {
                    public Object processArrayValue(Object value,
                                                    JsonConfig arg1) {
                        // TODO Auto-generated method stub
                        return process(value);
                    }

                    public Object processObjectValue(String key,
                                                     Object value, JsonConfig arg2) {
                        // TODO Auto-generated method stub
                        return process(value);
                    }

                    public Object process(Object value) {
                        try {
                            if (value instanceof String) {
                                return URLDecoder.decode(value.toString(), "UTF-8");
                            }
                            return value == null ? "" : value.toString();
                        } catch (Exception e) {
                            return "";
                        }
                    }
                }
        );
        return jsonConfig;
    }
//    public SocketBean parseData(String resData){//resData为JSON字符串
//        JsonConfig jsonConfig = getDecodeJSONConfig();
//        JSONObject json = JSONObject.fromObject(resData, jsonConfig);
//        if(json.get("head").get())
//    /*
//     * 在JSONObject.toBean的时候，如果转换的类中有集合,
//     * 可以先定义：Map<String, Class> classMap = new HashMap<String, Class>();
//     * 然后在classMap中put你要转换的类中的集合名,如:
//     */
//        Map<String, Class> classMap = new HashMap<String, Class>();
//        classMap.put("dataPoints", M100DataObject.class);//dataPoints 为 属性名称
//    /*
//     * 然后在toBean()的时候把参数加上, 如:
//     */
//        GetM100DataResponse response = (GetM100DataResponse)JSONObject.toBean(json, GetM100DataResponse.class, classMap);
//        return response;
//    }

}
