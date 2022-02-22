package com.ksf.aop.aspect;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AspectController {

    @TestAnnotation(value = "OK")
    @RequestMapping("/aspect/ann/have")
    public String haveAnnotation() {
        System.out.println("haveAnnotation");
        return "haveAnnotation";
    }

    @RequestMapping("/aspect/ann/not")
    public String notAnnotation() {
        System.out.println("notAnnotation");
        return "notAnnotation";
    }
}
