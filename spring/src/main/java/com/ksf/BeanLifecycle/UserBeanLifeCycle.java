package com.ksf.BeanLifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/10 14:46
 */
@Component
public class UserBeanLifeCycle implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanDefinitionRegistryPostProcessor, InitializingBean, DisposableBean, BeanPostProcessor {

    private BeanDefinitionRegistry registry;

    private ApplicationContext applicationContext;

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("setApplicationContext");
    }

    //BeanDefinitionRegistryPostProcessor接口在读取项目中的beanDefinition之后执行，提供的一个补充扩展接口，用来动态注册beanDefinition
    //调用点：在PostProcessorRegistrationDelegate中
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
        System.out.println("postProcessBeanDefinitionRegistry");
        BeanDefinitionBuilder datasourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(Order.class);

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "百事可乐");
        properties.put("code", "posdaqe");
        properties.forEach(datasourceBuilder::addPropertyValue);

        AbstractBeanDefinition beanDefinition = datasourceBuilder.getBeanDefinition();
        registry.registerBeanDefinition("orderBean", beanDefinition);
        System.out.println("registry bean :orderBean");
    }


    //BeanFactoryPostProcessor 用来在读取所有的beanDefinition信息之后，实例化之前，通过该接口可进一步自行处理，比如修改beanDefinition等
    //调用点在上面第一个扩展接口之后，也在PostProcessorRegistrationDelegate
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        beanFactory.addBeanPostProcessor();
        System.out.println("postProcessBeanFactory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserBeanPostProcessor afterPropertiesSet");
    }


    //需要手动调用容器的close方法
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(User.class)) {
            System.out.println("postProcessBeforeInitialization");
        }
        if (bean.getClass().equals(Order.class)) {
            System.out.println(beanName + "   " + bean.toString());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(User.class)) {
            System.out.println("postProcessAfterInitialization");
        }
        return bean;
    }


}
