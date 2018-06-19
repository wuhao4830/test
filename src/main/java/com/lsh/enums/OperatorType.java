package com.lsh.enums;

/**
 * Created by wuHao on 18/6/12.
 */
//type： 1.注册
public enum OperatorType {

    REGISTER(1),
    DOOR_MONITOR(2);

    OperatorType(int value) {
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
