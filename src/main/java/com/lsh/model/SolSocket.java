package com.lsh.model;

/**
 * Created by wuhao on 2018/6/5.
 */
public class SolSocket {
    private String relationId;
    private String uid;
    private Long timestamp;
    private int status;
    private String messageId;
    private String message;
    private SolBean body;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SolBean getBody() {
        return body;
    }

    public void setBody(SolBean body) {
        this.body = body;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "SolSocket{" +
                "relationId='" + relationId + '\'' +
                ", uid='" + uid + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", body=" + body +
                '}';
    }
}
