package com.ksf.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangchengkang
 * @date 2022/7/6 11:38
 */
@RestController
public class TestConController {

    private TestService testService;

    //构造器注入可以不用@Autowired
    public TestConController(TestService testService) {
        this.testService = testService;
    }


    @GetMapping("/test/")
    public String tset(@RequestParam("name") String name, @RequestParam(value = "address", required = false, defaultValue = "wuhan") List<String> address) {
        System.out.println("name=" + name);
        address.forEach(t -> System.out.println("address=" + t));
        return testService.tset();
    }

}
