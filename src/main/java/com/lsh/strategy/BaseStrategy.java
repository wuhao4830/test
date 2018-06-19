package com.lsh.strategy;

import com.lsh.hardwareService.HardWareBaseService;

import java.util.List;

/**
 * Created by wuhao on 2018/6/4.
 */
public interface BaseStrategy {

    List<HardWareBaseService> getHardWare();
}
