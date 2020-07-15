package com.ksf.aop.aspect;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @TestAnnotation(value = "OK")
    @RequestMapping("/have")
    public String haveAnnotation() {
        System.out.println("haveAnnotation");
        return "haveAnnotation";
    }

    @RequestMapping("/not")
    public String notAnnotation() {
        System.out.println("notAnnotation");
        return "notAnnotation";
    }
}
