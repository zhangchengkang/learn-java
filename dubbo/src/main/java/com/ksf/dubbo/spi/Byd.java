package com.ksf.dubbo.spi;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/18 17:39
 */
public class Byd implements Car {
    @Override
    public void getName() {
        System.out.println("BYD");
    }
}
