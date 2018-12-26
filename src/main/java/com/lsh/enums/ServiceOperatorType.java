package com.lsh.enums;

/**
 * Created by wuHao on 18/6/12.
 */
//type： 1.注册
public enum ServiceOperatorType {

    REGISTER("1"),
    DOOR_STATUS("2");

    ServiceOperatorType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SocketType{" +
                "value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
}
