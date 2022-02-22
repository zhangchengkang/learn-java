package com.ksf.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/21 14:15
 */
@RestController
public class EventPublishAwareController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RequestMapping("/aware/event")
    public String publishEvent() {
        applicationEventPublisher.publishEvent(new EventDemo(this, "applicationEventPublisher 发布事件"));
        return "事件发布成功";
    }

}
