package com.ksf.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 15:46
 */
public class AdaptiveExtProxy
//        implements AdaptiveExt
{

//    @Override
//    public String echo(String msg, URL url) {
//        // 非空检查
//        if (url == null) {
//            throw new IllegalArgumentException("url == null");
//        }
//
//        // 1、从 URL 中获取 AdaptiveExt 名称
//        String name = url.getParameter("adaptive.ext");
//        if (name == null ) {
//            throw new IllegalArgumentException("name == null");
//        }
//
//        // 2、调用 SPI 动态加载具体的 AdaptiveExt
//        AdaptiveExt adaptiveExt = ExtensionLoader
//                .getExtensionLoader(AdaptiveExt.class)
//                .getExtension(name);
//
//        // 3、调用具体的方法
//        return adaptiveExt.echo(msg, url);
//    }
}
