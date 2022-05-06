package com.ksf.BeanLifecycle;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/10 15:56
 */
//需要在spring.factories里注册(Spring SPI扩展)
public class UserApplicationContextInitializer implements ApplicationContextInitializer {
    ////在容器刷新之前调用。可以在整个spring容器还没被初始化之前做一些事情。
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.println("ApplicationContextInitializer--initialize");
    }
}
