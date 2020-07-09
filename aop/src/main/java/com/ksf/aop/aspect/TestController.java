package com.ksf.aop.aspect;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @TestAnnotation(value = "yes,you are right")
    @RequestMapping("/have")
    public String haveAnnotation() {
        return "haveAnnotation";
    }

    @RequestMapping("/not")
    public String notAnnotation() {
        return "notAnnotation";
    }
}
