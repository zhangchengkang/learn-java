package com.ksf.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 15:54
 */
public class MybatisAdaptiveExtImpl implements AdaptiveExt{
    @Override
    public String echo(String msg, URL url) {
        return "mybatis";
    }
}
