package com.lsh.enums;

/**
 * Created by panxudong on 17/4/20.
 */
public enum HardWareType {

    DOOR(1);

    HardWareType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HardWareType{" +
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
