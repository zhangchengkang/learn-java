package com.ksf.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 14:34
 */
@Activate(group = {"group1","group2"})
public class GroupActivateExtImpl implements ActivateExt {
    @Override
    public String echo(String msg) {
        return msg;
    }
}
