package com.lsh.model;

/**
 * Created by wuhao on 2018/5/31.
 */
public class Hardware {
    public int type;
    public String ip;
    public int port;
    public String macStrr;
    public String macId;
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMacStrr() {
        return macStrr;
    }

    public void setMacStrr(String macStrr) {
        this.macStrr = macStrr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    @Override
    public String toString() {
        return "Hardware{" +
                "type=" + type +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", macStrr='" + macStrr + '\'' +
                ", macId='" + macId + '\'' +
                '}';
    }
}
