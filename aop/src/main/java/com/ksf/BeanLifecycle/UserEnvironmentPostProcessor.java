package com.ksf.BeanLifecycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/10 15:56
 */
//需要再spring.factories里注册
public class UserEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Integer port = environment.getProperty("server.port", Integer.class, 8080);
        System.out.println("postProcessEnvironment:" + port);
    }
}
