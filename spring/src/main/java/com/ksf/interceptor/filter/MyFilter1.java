package com.ksf.interceptor.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 14:42
 */
//使用原生servlet注解(@WebFilter)定义Filter
// TODO urlPatterns失效了
@Component
@WebFilter(urlPatterns = {"/filter1/*"}, filterName = "myFilter1")
public class MyFilter1 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter1===init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter1===执行");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
