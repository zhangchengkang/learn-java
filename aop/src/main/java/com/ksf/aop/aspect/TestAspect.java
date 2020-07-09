package com.ksf.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class TestAspect {
    @Before("@annotation(test)")
    public void before(JoinPoint point, TestAnnotation test) {
        System.out.println("Before method run :" + test.value());
    }

    @After("@annotation(test)")
    public void after(JoinPoint point, TestAnnotation test) {
        System.out.println("After method run :" + test.value());
    }
}
