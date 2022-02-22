package com.ksf.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/19 15:37
 */
public class EventDemo extends ApplicationEvent {

    private String eventName;

    public EventDemo(Object source,String eventName) {
        super(source);
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
