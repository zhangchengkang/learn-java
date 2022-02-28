package com.ksf.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 15:24
 */
@SPI
public interface AdaptiveExt {

    @Adaptive(value = {"adaptiveName"})
    String echo(String msg, URL url);
}
