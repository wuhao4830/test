package com.lsh.enums;

/**
 * Created by wuHao on 18/6/12.
 */
//service type： 1。请求  2。返回      gw：3。请求  4。返回
public enum SocketType {

    SERVICE_RESPONSE(2),
    CLIENT_RESPONSE(4),
    CLIENT_REQUEST(3),
    SERVICE_REQUEST(1);

    SocketType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SocketType{" +
                "value='" + value + '\'' +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int value;
}
