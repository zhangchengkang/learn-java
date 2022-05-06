package com.ksf.res;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangchengkang
 * @Date: 2022/3/15 11:48
 */
@Component
public class ResponseBodyWrapBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> handlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>(handlers);
        decorateHandlers(handlerMethodReturnValueHandlers);
        adapter.setReturnValueHandlers(handlerMethodReturnValueHandlers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                ResponseBodyWrapHandler decorator = new ResponseBodyWrapHandler(handler);
                handlers.set(handlers.indexOf(handler), decorator);
                break;
            }
        }
    }
}
