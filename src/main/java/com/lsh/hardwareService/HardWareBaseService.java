package com.lsh.hardwareService;

import com.lsh.model.Hardware;

import java.util.Map;

/**
 * Created by wuhao on 2018/6/1.
 */
public interface HardWareBaseService {
    int getStatus();
    boolean isAilve();
    Hardware getHardware();
    Object operator(String command,Object cmdParams)throws Exception;
}
