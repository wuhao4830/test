package com.lsh.model;

/**
 * Created by wuhao on 2018/6/5.
 */
public class SolBean {

    private Integer type=1;
    private Object cmdParams;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getCmdParams() {
        return cmdParams;
    }

    public void setCmdParams(Object cmdParams) {
        this.cmdParams = cmdParams;
    }
}
