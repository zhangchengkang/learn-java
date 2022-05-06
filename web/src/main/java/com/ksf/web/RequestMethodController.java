package com.ksf.web;


import org.springframework.web.bind.annotation.*;


/**
 * • Rest风格支持（使用HTTP请求方式动词来表示对资源的操作）
 * • 以前：/getUser   获取用户     /deleteUser 删除用户    /editUser  修改用户       /saveUser 保存用户
 * • 现在： /user    GET-获取用户    DELETE-删除用户     PUT-修改用户      POST-保存用户
 * • 核心Filter；HiddenHttpMethodFilter
 * • 用法： 表单method=post，<input name="_method" type="hidden" value="PUT"/>
 * • PostMan直接发送Put、delete等方式请求，无需Filter
 */
@RestController
public class RequestMethodController {

    @RequestMapping("/bug.jpg")
    public String hello(){
        return "aaaa";
    }

//    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping("/user")
    public String getUser(){

        return "GET-张三";
    }

//    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @PostMapping("/user")
    public String saveUser(){
        return "POST-张三";
    }


//    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    @PutMapping("/user")
    public String putUser(){
        return "PUT-张三";
    }

    @DeleteMapping("/user")
//    @RequestMapping(value = "/user",method = RequestMethod.DELETE)
    public String deleteUser(){
        return "DELETE-张三";
    }





}
