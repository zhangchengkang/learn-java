package com.ksf.interceptor.methodInterceptor;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MethodInterceptorController {

    @Autowired
    private MethodInterceptorService methodInterceptorService;

    @RequestMapping("/methodInterceptor")
    public String test() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(methodInterceptorService);
        proxyFactory.addAdvice(new MyMethodInterceptor());

        Object proxy = proxyFactory.getProxy();
        MethodInterceptorService methodInterceptor = (MethodInterceptorService) proxy;

        return methodInterceptor.doSomeThing("通过代理工厂设置代理对象，拦截代理方法");
    }
}
