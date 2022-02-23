package com.ksf.interceptor.handlerInterceptor;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 14:13
 */
@Component
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final List<String> ignorePatterns = new ArrayList<>();

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private final PathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (ignored(httpServletRequest)) {
            System.out.println("skip preHandle");
            return true;
        } else {
            System.out.println("preHandle");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
    }

    private boolean ignored(HttpServletRequest request) {
        if (CollectionUtils.isNotEmpty(ignorePatterns)) {
            String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
            return ignorePatterns.stream().anyMatch(ignorePattern -> pathMatcher.match(ignorePattern, lookupPath));
        }
        return false;
    }

    public static void ignorePathPatterns(String... patterns) {
        ignorePatterns.addAll(Arrays.asList(patterns));
    }
}
