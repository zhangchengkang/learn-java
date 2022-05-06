package com.ksf.web;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: learn-spring-boot
 * @description:
 * @author: kangshifu
 * @create: 2021-06-01 21:09
 **/
public class ResourcesController {

    /**
     * 只要静态资源放在类路径下： called /static (or /public or /resources or /META-INF/resources
     * 访问路径 ： 当前项目根路径/ + 静态资源名
     * http://localhost:8888/mysql.png
     * http://localhost:8888/blog.jpg
     */

    /**
     * 原理： 静态映射/**。
     * 请求进来，先去找Controller看能不能处理。
     * 不能处理的所有请求又都交给静态资源处理器。
     * 静态资源也找不到则响应404页面
     * @return
     */
    @RequestMapping("/mysql.png")
    public String hello() {
        return "视频讲的返回这个行字,但是我返回的是mysql.png图片";
    }

    @RequestMapping("/index.html")
    public String index() {
        return "fffffff";
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
