package com.ksf.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: zhangchengkang
 * @Date: 2020/8/18 14:49
 */
//使用spring boot提供的FilterRegistrationBean注册Filter
public class MyFilter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter2===init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter2===执行");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
