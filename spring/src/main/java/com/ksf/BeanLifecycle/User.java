package com.ksf.BeanLifecycle;

import org.springframework.beans.factory.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/10 14:38
 */
@Component
@ConditionalOnProperty(prefix = "user", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "user")
public class User implements InitializingBean {
    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        System.out.println("userName属性赋值");
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        System.out.println("passWord属性赋值");
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public User() {
        System.out.println("执行user构造");
    }

    //这个注解跟xml写init-method一样
    @PostConstruct
    public void init() {
        System.out.println("annotation init" + toString());
    }

    //只对自己这个bean有效
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("user afterPropertiesSet");
    }
}
