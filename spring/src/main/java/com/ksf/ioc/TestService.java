package com.ksf.ioc;

import org.springframework.stereotype.Service;

/**
 * @author zhangchengkang
 * @date 2022/7/6 11:39
 */
@Service
public class TestService {

    public String tset() {
        System.out.println("test success");
        return "success";
    }
}
