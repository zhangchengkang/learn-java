package com.ksf.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 14:43
 */
@Activate(group = "value",value = "value")
public class ValueActivateExtImpl implements ActivateExt {
    @Override
    public String echo(String msg) {
        return msg;
    }
}
