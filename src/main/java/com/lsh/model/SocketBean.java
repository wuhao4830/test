package com.lsh.model;

import java.util.Map;

/**
 * Created by wuhao on 2018/6/5.
 */
public class SocketBean {

    private Map<String,Object> head;
    private Map<String,Object> body;

    public Map<String, Object> getHead() {
        return head;
    }

    public void setHead(Map<String, Object> head) {
        this.head = head;
    }

    public Map getBody() {
        return body;
    }

    public void setBody(Map body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SocketBean{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
