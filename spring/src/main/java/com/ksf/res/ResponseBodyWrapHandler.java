package com.ksf.res;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: zhangchengkang
 * @Date: 2022/3/15 11:46
 */
public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 如果类或者方法含有不包装注解则忽略包装
        IgnoreResponseWrapper wrapper = returnType.getDeclaringClass().getAnnotation(IgnoreResponseWrapper.class);
        if (wrapper != null) {
            delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            return;
        }
        wrapper = returnType.getMethodAnnotation(IgnoreResponseWrapper.class);
        if (wrapper != null) {
            delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            return;
        }

        delegate.handleReturnValue(WebResponse.success(returnValue), returnType, mavContainer, webRequest);
    }
}
