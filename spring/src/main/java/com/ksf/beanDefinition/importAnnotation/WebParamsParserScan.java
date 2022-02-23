package com.ksf.beanDefinition.importAnnotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
import  com.ksf.beanDefinition.importAnnotation.WebParamsParserRegistrar;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/23 10:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WebParamsParserRegistrar.class)
public @interface WebParamsParserScan {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}
