package com.ksf.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/19 15:40
 */
@Component
public class EventListenerDemo implements ApplicationListener<EventDemo> {

    /**
     * applicationContext ApplicationEventPublisherAware 都能发布事件
     * @param event
     */
    @Override
    public void onApplicationEvent(EventDemo event) {
        System.out.println(event.getEventName());
    }
}
