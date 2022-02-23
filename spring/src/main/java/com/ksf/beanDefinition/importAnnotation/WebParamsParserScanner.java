package com.ksf.beanDefinition.importAnnotation;

import com.ksf.interceptor.handlerInterceptor.MyHandlerInterceptor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;



/**
 * @Author: zhangchengkang
 * @Date: 2022/2/23 10:41
 */
public class WebParamsParserScanner extends ClassPathBeanDefinitionScanner {

    private final ClassLoader classLoader;

    private final PathMatcher pathMatcher = new AntPathMatcher();


    public WebParamsParserScanner(BeanDefinitionRegistry registry, ClassLoader classLoader) {
        super(registry, false);
        this.classLoader = classLoader;
    }

    public void registerFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        this.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            if (candidates.isEmpty()) {
                continue;
            }
            processBeanDefinitions(candidates);
        }
        return new HashSet<>();
    }

    private void processBeanDefinitions(Set<BeanDefinition> beanDefinitions) {
        Set<String> ignorePatterns = new HashSet<>();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> controllerType = ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.classLoader);
            String typePath = getTypeRequestMapping(controllerType);
            Method[] methods = controllerType.getMethods();
            boolean classIgnored = controllerType.isAnnotationPresent(IgnoreParse.class);
            for (Method method : methods) {
                if (!classIgnored) {
                    if (!method.isAnnotationPresent(IgnoreParse.class)) {
                        continue;
                    }
                }

                String methodPath = getMethodRequestMapping(method);
                if (Objects.isNull(methodPath)) {
                    continue;
                }
                String path = pathMatcher.combine(typePath, methodPath);
                ignorePatterns.add(path.startsWith("/") ? path : ("/" + path));
            }
        }
        MyHandlerInterceptor.ignorePathPatterns(ignorePatterns.toArray(new String[0]));
    }

    private String getTypeRequestMapping(Class<?> controllerType) {
        Assert.notNull(controllerType, "'controllerType' must not be null");
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(controllerType, RequestMapping.class);
        if (requestMapping == null) {
            return "/";
        }
        String[] paths = requestMapping.path();
        if (ObjectUtils.isEmpty(paths) || StringUtils.isEmpty(paths[0])) {
            return "/";
        }
        if (paths.length > 1) {
            System.out.println("Multiple paths on controller " + controllerType.getName() + ", using first one");
        }
        return paths[0];
    }

    private String getMethodRequestMapping(Method method) {
        Assert.notNull(method, "'method' must not be null");
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }
        String[] paths = requestMapping.path();
        if (ObjectUtils.isEmpty(paths) || StringUtils.isEmpty(paths[0])) {
            return "/";
        }
        if (paths.length > 1) {
            System.out.println("Multiple paths on method " + method.toGenericString() + ", using first one");
        }
        return paths[0];
    }
}
