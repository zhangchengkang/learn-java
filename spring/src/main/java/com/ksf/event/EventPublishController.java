package com.ksf.event;

import com.ksf.event.EventDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/19 15:43
 */
@RestController
public class EventPublishController {

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/event")
    public String publishEvent() {
        applicationContext.publishEvent(new EventDemo(this, "applicationContext 发布事件"));
        return "事件发布成功";
    }
}
