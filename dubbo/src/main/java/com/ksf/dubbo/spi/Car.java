package com.ksf.dubbo.spi;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/18 17:38
 */
@SPI//dubbo spi
public interface Car {
    void getName();
}
