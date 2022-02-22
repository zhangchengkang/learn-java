package com.ksf.interceptor.handlerInterceptor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 14:34
 */
@RestController
public class HandlerInterceptorController {

    @RequestMapping("/handler/interceptor")
    public String test() {
        System.out.println("handlerInterceptor");
        return "handlerInterceptor";
    }
}
