package com.ksf.BeanLifecycle;

import lombok.ToString;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/11 10:22
 */
@ToString
public class Order {
    private String name;
    private String code;

    public Order() {
        System.out.println("执行order构造");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        System.out.println("setCode");
        this.code = code;
    }
}
