package com.ksf.interceptor.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 14:54
 */
@Configuration
public class MyFilter2Config {

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new MyFilter2());
        registrationBean.addUrlPatterns("/filter2/*");
        registrationBean.setName("myFilter2");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
