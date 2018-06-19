package com.lsh.model;

import java.util.Map;

/**
 * Created by wuhao on 2018/6/5.
 */
public class SocketHead {

    private int status ;
    private int type;
    private String gatewayId;
    private String message;
    private String messageId;
    private int timestamp;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SocketHead{" +
                "status=" + status +
                ", type=" + type +
                ", gatewayId='" + gatewayId + '\'' +
                ", message='" + message + '\'' +
                ", messageId='" + messageId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
