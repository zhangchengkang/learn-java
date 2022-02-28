package com.ksf.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 14:41
 */
@Activate(group = "order",order = 2)
public class OrderActivateExtImpl2 implements ActivateExt {
    @Override
    public String echo(String msg) {
        return msg;
    }
}
