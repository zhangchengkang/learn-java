package com.ksf.dubbo.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Filter;

import java.util.ServiceLoader;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/18 16:23
 */
public class SPITest {
    public static void main(String[] args) {

        System.out.println("====java SPI====");
        //java SPI 只能放 META-INF/services/ 下  且文件名为类全限定名
        ServiceLoader<Car> serviceLoader = ServiceLoader.load(Car.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Car::getName);

        System.out.println("====dubbo SPI====");
        //dubbo SPI 可以放这个三个地方 META-INF/services/   META-INF/dubbo/   META-INF/dubbo/internal/
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        Car byd = extensionLoader.getExtension("byd");
        byd.getName();
        Car bwm = extensionLoader.getExtension("bwm");
        bwm.getName();
    }
}
