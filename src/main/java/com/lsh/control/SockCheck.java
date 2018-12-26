package com.lsh.control;

import com.lsh.data.SocketData;
import com.lsh.model.RelationSocket;
import com.lsh.utils.PropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.*;

/**
 * Created by wuhao on 2018/11/29.
 */
public class SockCheck {

    private static final Logger logger = LoggerFactory.getLogger(SockCheck.class);

    public void start() throws Exception {
        Timer timer = new Timer();
        try {

            final Long refreshCapacity = Long.valueOf(PropsUtils.get("client.socket.refreshCapacity"));
            logger.info("start socket timer refreshCapacity:" + refreshCapacity);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        Map<String,RelationSocket> socket2uidMap = SocketData.getSocket2uidMap();
                        List<String> uids = new ArrayList<String>();
                        logger.info("begin run check alive socket ,size:"+socket2uidMap.size());
                        for (Map.Entry<String, RelationSocket> entry : socket2uidMap.entrySet()) {
                            logger.info("check uid:"+entry.getKey()+",-relationId:"+entry.getValue().getRelationId());
                            checkSocketConn(entry.getValue().getSocket(),entry.getKey(),uids);
                        }
                        for(String uid:uids){
                            SocketData.removeSocket(uid);
                        }
                        logger.info("check alive socket end,size:"+socket2uidMap.size());
                    } catch (Throwable throwable) {
                        logger.error(throwable.getMessage(), throwable);
                    }

                }
            }, 0, refreshCapacity);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    public void checkSocketConn(Socket client,String uid,List<String> delRelationIds) {
        try {
            client.sendUrgentData(0xFF);
        }catch (Exception e){
            delRelationIds.add(uid);
        }
    }
}
