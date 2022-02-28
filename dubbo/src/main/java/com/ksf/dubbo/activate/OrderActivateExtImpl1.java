package com.ksf.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 14:39
 */
@Activate(group = "order",order = 1)
public class OrderActivateExtImpl1 implements ActivateExt {
    @Override
    public String echo(String msg) {
        return msg;
    }
}
