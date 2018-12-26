package com.lsh.data;

import com.lsh.model.RelationSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhao on 2018/6/13.
 */
public class SocketData {
    //用户注册数据(需要注册硬件的获取服务)
    private static Map<String,RelationSocket> socket2uidMap = new HashMap<String, RelationSocket>();
    private static final Logger logger = LoggerFactory.getLogger(SocketData.class);


    public static Map<String,RelationSocket> getSocket2uidMap() {
        return socket2uidMap;
    }

    public static boolean saveSocket(RelationSocket relationSocket,String uid) {
        synchronized (socket2uidMap) {
            socket2uidMap.put(uid, relationSocket);
                return true;
        }
    }

    public static boolean removeSocket(String uid) {
        synchronized (socket2uidMap) {
            if (socket2uidMap.containsKey(uid)) {
                //注册过
                logger.info("remove SocketData:" +uid );
                socket2uidMap.remove(uid);
                return true;
            }
        }
        return false;
    }

    public static RelationSocket getSocket(String uid) {
        synchronized (socket2uidMap) {
            if (socket2uidMap.containsKey(uid)) {
                //注册过
                return socket2uidMap.get(uid);
            }
        }
        return null;
    }

}
