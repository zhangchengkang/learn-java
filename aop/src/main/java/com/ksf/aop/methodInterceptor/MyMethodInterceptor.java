package com.ksf.aop.methodInterceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 在spring中拦截器有两种，第一种是HandlerInterceptor，第二种是MethodInterceptor。
 * HandlerInterceptor是SpringMVC中的拦截器，它拦截的是Http请求的信息，优先于MethodInterceptor。
 * 而MethodInterceptor是springAOP的。
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = null;
        try {
            System.out.println("方法执行之前：" + invocation.getMethod().toString());

            result = invocation.proceed();

            System.out.println("方法执行之后：" + invocation.getMethod().toString());
            System.out.println("方法正常运行结果：" + result);

            return result;
        } catch (Exception e) {
            System.out.println("方法出现异常:" + e.toString());
            System.out.println("方法运行Exception结果：" + result);
            return result;
        }

    }
}
