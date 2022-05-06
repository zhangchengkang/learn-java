package com.ksf.BeanLifecycle;

import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/11 10:22
 */
@ToString
@Component
@ConditionalOnProperty(prefix = "order", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "order")
public class Order implements InitializingBean {
    private String name;
    private String code;

    @Autowired
    private User user;

    public Order() {
        System.out.println("执行order构造");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        System.out.println("setCode");
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //这个注解跟xml写init-method一样
    @PostConstruct
    public void init() {
        System.out.println("===Order: PostConstruct" );
    }

    //只对自己这个bean有效
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("===Order: afterPropertiesSet");
    }
}
