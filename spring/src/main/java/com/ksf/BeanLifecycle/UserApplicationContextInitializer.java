package com.ksf.BeanLifecycle;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/10 15:56
 */
//需要再spring.factories里注册
public class UserApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.println("ApplicationContextInitializer--initialize");
    }
}
