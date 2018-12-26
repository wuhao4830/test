package com.lsh.model;

import java.net.Socket;

/**
 * Created by wuhao on 2018/12/6.
 */
public class RelationSocket {

    private String relationId;

    private Socket socket;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public RelationSocket(String relationId, Socket socket) {
        this.relationId = relationId;
        this.socket = socket;
    }
}
