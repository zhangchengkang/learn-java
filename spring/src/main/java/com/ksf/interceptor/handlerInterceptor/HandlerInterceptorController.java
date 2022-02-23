package com.ksf.interceptor.handlerInterceptor;

import com.ksf.beanDefinition.importAnnotation.IgnoreParse;
import com.ksf.beanDefinition.importAnnotation.WebParamsParserScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 14:34
 */
@RestController
@WebParamsParserScan
public class HandlerInterceptorController {

    @RequestMapping("/handler/interceptor")
    public String test() {
        System.out.println("handlerInterceptor");
        return "handlerInterceptor";
    }

    @RequestMapping("/handler/interceptor/IgnoreParse")
    @IgnoreParse
    public String ignoreParse() {
        System.out.println("handlerInterceptor");
        return "handlerInterceptor";
    }
}
