package com.ksf.res;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangchengkang
 * @Date: 2022/3/15 14:13
 */
@RestController
public class TestController {

    @RequestMapping("/res/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/res/test1")
    public WebResponse test1() {
        return WebResponse.success("test1");
    }

    @RequestMapping("/res/test2")
    public ResponseEntity test2() {
        return new ResponseEntity<>(WebResponse.error("服务异常, 请稍后重试"), HttpStatus.OK);
    }
}
