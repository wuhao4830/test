package com.lsh.model;

import com.lsh.base.common.utils.DateUtils;

/**
 * Created by wuhao on 2018/6/5.
 */
public class SocketMessage {

    /**
     *service type： 1.请求  2.返回      gw：3.请求  4.返回
    * */
    private int type;
    private int operatorType;
    private Object data;
    private Long createdAt = DateUtils.getCurrentSeconds();
    private String messageId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(int operatorType) {
        this.operatorType = operatorType;
    }

    public SocketMessage(int type, Object data, String messageId,int operatorType) {
        this.type = type;
        this.data = data;
        this.messageId = messageId;
        this.operatorType = operatorType;
    }

    public SocketMessage() {
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "type=" + type +
                ", operatorType=" + operatorType +
                ", data=" + data +
                ", createdAt=" + createdAt +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
