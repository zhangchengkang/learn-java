package com.ksf.dubbo.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.ServiceLoader;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/18 16:23
 */
public class SPITest {
    public static void main(String[] args) {

        System.out.println("====java SPI====");
        ServiceLoader<Car> serviceLoader = ServiceLoader.load(Car.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Car::getName);

        System.out.println("====dubbo SPI====");
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        Car byd = extensionLoader.getExtension("byd");
        byd.getName();
        Car bwm = extensionLoader.getExtension("bwm");
        bwm.getName();
    }
}
