package com.lsh.enums;

/**
 * Created by panxudong on 17/4/20.
 */
public enum HardWareType {

    //门
    DOOR("1"),
    //结算台（网关）
    SETTLEMENT("2");

    HardWareType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HardWareType{" +
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
