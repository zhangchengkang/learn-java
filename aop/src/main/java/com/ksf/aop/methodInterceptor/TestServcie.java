package com.ksf.aop.methodInterceptor;

import org.springframework.stereotype.Component;

@Component
public class TestServcie {

    public String doSomeThing(String someThing) {
        return "执行被拦截的方法：" + someThing;
    }
}
