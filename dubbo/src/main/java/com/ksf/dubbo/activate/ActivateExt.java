package com.ksf.dubbo.activate;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 14:00
 */
@SPI
public interface ActivateExt {
    String echo(String msg);
}
