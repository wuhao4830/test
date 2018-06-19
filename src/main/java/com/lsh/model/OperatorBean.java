package com.lsh.model;

/**
 * Created by wuhao on 2018/6/5.
 */
public class OperatorBean {

    private String cmd;
    private String macId;
    private Object cmdParams;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public Object getCmdParams() {
        return cmdParams;
    }

    public void setCmdParams(Object cmdParams) {
        this.cmdParams = cmdParams;
    }

    @Override
    public String toString() {
        return "SocketBean{" +
                "cmd='" + cmd + '\'' +
                ", macId='" + macId + '\'' +
                ", cmdParams=" + cmdParams +
                '}';
    }
}
