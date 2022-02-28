package com.ksf.dubbo.activate;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.List;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/28 14:48
 */
public class ActivateTest {
    public static void main(String[] args) {
        ExtensionLoader<ActivateExt> loader = ExtensionLoader.getExtensionLoader(ActivateExt.class);
        URL url = URL.valueOf("test://localhost/test");

        print(loader, url, "default_group");
        print(loader, url, "group2");
        print(loader, url, "order");

        //@Activate(value = {"value"}, group = {"value"})来激活扩展,不加找不到
        url = url.addParameter("value", "value");
        print(loader, url, "value");
    }

    public static void print(ExtensionLoader<ActivateExt> loader, URL url, String group) {
        List<ActivateExt> list = loader.getActivateExtension(url, new String[]{}, group);
        System.out.println(list.size());
        System.out.println(list.get(0).getClass());
    }
}
