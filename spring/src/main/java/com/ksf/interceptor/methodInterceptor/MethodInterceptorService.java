package com.ksf.interceptor.methodInterceptor;

import org.springframework.stereotype.Component;

@Component
public class MethodInterceptorService {

    public String doSomeThing(String someThing) {
        return "执行被拦截的方法：" + someThing;
    }
}
