package com.ksf.interceptor.filter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 15:00
 */
@RestController
public class FilterController {

    @RequestMapping("/filter1/test")
    public String filter1() {
        System.out.println("filter1");
        return "filter1";
    }

    @RequestMapping("/filter2/test")
    public String filter2() {
        System.out.println("filter2");
        return "filter2";
    }
}
