package com.ksf.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 15:47
 */
public class AdaptiveTest {
    public static void main(String[] args) {
        ExtensionLoader<AdaptiveExt> extExtensionLoader = ExtensionLoader.getExtensionLoader(AdaptiveExt.class);
        AdaptiveExt adaptiveExt = extExtensionLoader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?adaptiveName=spring");
        System.out.println(adaptiveExt.echo("d", url));
    }
}
