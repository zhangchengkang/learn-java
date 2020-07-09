package com.ksf.aop.methodInterceptor;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("testController1")
public class TestController {

    @Autowired
    private TestServcie testServcie;

    @RequestMapping("/test")
    public String test() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(testServcie);
        proxyFactory.addAdvice(new MyMethodInterceptor());

        Object proxy = proxyFactory.getProxy();
        TestServcie methodInterceptor = (TestServcie) proxy;

        return methodInterceptor.doSomeThing("通过代理工厂设置代理对象，拦截代理方法");
    }
}
