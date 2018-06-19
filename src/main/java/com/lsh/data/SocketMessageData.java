package com.lsh.data;

import com.lsh.hardwareService.HardWareBaseService;
import com.lsh.model.Hardware;
import com.lsh.model.SocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhao on 2018/6/13.
 */
public class SocketMessageData {
    //硬件注册数据(需要注册硬件的获取服务)
    private static Map<String,SocketMessage> socket2MessageMap = new HashMap<String, SocketMessage>();
    private static final Logger logger = LoggerFactory.getLogger(SocketMessageData.class);


    public static Map<String, SocketMessage> getSocket2MessageMap() {
        return socket2MessageMap;
    }

    public static boolean saveMessage(SocketMessage message) {
        synchronized (socket2MessageMap) {
            if (!socket2MessageMap.containsKey(message.getMessageId())) {
                //没有存储过（1.放入到本地注册库中）
                socket2MessageMap.put(message.getMessageId(), message);
                return true;
            }
        }
        return false;
    }

    public static boolean removeMessage(SocketMessage message) {
        synchronized (socket2MessageMap) {
            if (socket2MessageMap.containsKey(message.getMessageId())) {
                //注册过
                logger.info("remove SocketMessageData:" +message );
                socket2MessageMap.remove(message.getMessageId());
                return true;
            }
        }
        return false;
    }

    public static SocketMessage getMessage(String messageId) {
        synchronized (socket2MessageMap) {
            if (socket2MessageMap.containsKey(messageId)) {
                //注册过
                return socket2MessageMap.get(messageId);
            }
        }
        return null;
    }

}
