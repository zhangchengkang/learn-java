package com.ksf.factory.objectprovider;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/22 14:21
 */
@Component
public class DispatchFactory {

    private static final Map<HandlerType, DispatchHandler> handlers = new HashMap<>();

    public DispatchFactory(ObjectProvider<DispatchHandler[]> objectProviders) {
        DispatchHandler[] ifAvailable = objectProviders.getIfAvailable();
        Stream.of(ifAvailable).forEach(handler -> handlers.put(handler.getHandlerType(), handler));
    }

    public DispatchHandler getHandler(HandlerType handlerType) {
        return handlers.get(handlerType);
    }

    public enum HandlerType {
        Product,
        Task
    }
}
