package com.ksf.aop.aspect;

import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(1)
@Component
public class TestAspect {

    @Pointcut("@annotation(TestAnnotation)")
    public void testAnnotationPointCut() {
    }

    @Before("testAnnotationPointCut()")
    public void before(JoinPoint point) {
        TestAnnotation annotation = getTestAnnotation(point);
        if (annotation == null) {
            throw new IllegalStateException("Wrong state for TestAnnotation annotation");
        }

        System.out.println("Before method run :" + annotation.value());
    }

    private TestAnnotation getTestAnnotation(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(TestAnnotation.class);
    }

    //上面的简写
    @After("@annotation(test)")
    public void after(JoinPoint point, TestAnnotation test) {
        System.out.println("After method run :" + test.value());
    }

    @SneakyThrows
    @Around("@annotation(test)")
    public Object around(ProceedingJoinPoint point, TestAnnotation test) {
        System.out.println("Around before method run :" + test.value());
        Object object = point.proceed();
        System.out.println("Around after method run :" + test.value());
        return object;
    }
}
