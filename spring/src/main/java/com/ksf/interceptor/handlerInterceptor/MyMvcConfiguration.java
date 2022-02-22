package com.ksf.interceptor.handlerInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 14:16
 */
@Component
public class MyMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private MyHandlerInterceptor myHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor)
                .addPathPatterns("/handler/**")
                .excludePathPatterns("/n/handler/**");
    }
}
