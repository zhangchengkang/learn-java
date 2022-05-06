package com.ksf.res;

import java.lang.annotation.*;

/**
 * @Author: zhangchengkang
 * @Date: 2022/3/15 13:44
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreResponseWrapper {
}
