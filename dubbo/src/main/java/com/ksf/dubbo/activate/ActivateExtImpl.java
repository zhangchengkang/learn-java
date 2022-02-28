package com.ksf.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 14:32
 */
@Activate(group = "default_group")
public class ActivateExtImpl implements ActivateExt {
    @Override
    public String echo(String msg) {
        return msg;
    }
}
