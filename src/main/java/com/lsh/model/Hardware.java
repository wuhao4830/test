package com.lsh.model;

import java.util.Map;

/**
 * Created by wuhao on 2018/5/31.
 */
public class Hardware {
    public String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }


    public static Hardware map2Bean(Map ori) {
        Hardware hardware = new Hardware();
        hardware.setIp(ori.get("ip").toString());
        hardware.setPort(Integer.valueOf(ori.get("port").toString()));
        hardware.setType(ori.get("type").toString());
        hardware.setMacId(ori.get("macId").toString());
        hardware.setMacStrr(ori.get("macStrr").toString());
        return hardware;
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
