package com.ksf.beanDefinition.importAnnotation;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import com.ksf.beanDefinition.importAnnotation.WebParamsParserScanner;

import java.util.*;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/23 10:35
 */

public class WebParamsParserRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware {

    private ClassLoader classLoader;

    private ResourceLoader resourceLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("setBeanClassLoader");
        this.classLoader = classLoader;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        System.out.println("setResourceLoader");
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("ImportBeanDefinitionRegistrar");
        WebParamsParserScanner scanner = new WebParamsParserScanner(registry, classLoader);
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        scanner.registerFilters();
        scanner.doScan(getPackagesToScan(importingClassMetadata).toArray(new String[0]));
    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(WebParamsParserScan.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(attributes);
        String[] values = annotationAttributes.getStringArray("value");
        String[] basePackages = annotationAttributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = annotationAttributes.getClassArray("basePackageClasses");

        // Appends value array attributes
        Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(values));
        packagesToScan.addAll(Arrays.asList(basePackages));
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }

        if (packagesToScan.isEmpty()) {
            return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }

        return packagesToScan;
    }
}
