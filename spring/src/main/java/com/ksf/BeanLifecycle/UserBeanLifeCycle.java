package com.ksf.BeanLifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/10 14:46
 */
@Component
public class UserBeanLifeCycle implements
        BeanNameAware,
        BeanFactoryAware,
        ApplicationContextAware,
        BeanDefinitionRegistryPostProcessor,
        InstantiationAwareBeanPostProcessor,
        SmartInstantiationAwareBeanPostProcessor,
        MergedBeanDefinitionPostProcessor,
        BeanPostProcessor,
        InitializingBean,
        CommandLineRunner,
        DisposableBean {

    private BeanDefinitionRegistry registry;

    private ApplicationContext applicationContext;

    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware--setBeanName");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware--setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("ApplicationContextAware--setApplicationContext");
    }


    //BeanDefinitionRegistryPostProcessor接口在读取项目中的beanDefinition之后执行，提供的一个补充扩展接口，用来动态注册beanDefinition
    //调用点：在PostProcessorRegistrationDelegate中
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
        System.out.println("BeanDefinitionRegistryPostProcessor--postProcessBeanDefinitionRegistry");

        //todo 这玩意会使user无属性注入
//        Object user = applicationContext.getBean("user");

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "百事可乐");
        properties.put("code", "posdaqe");

        BeanDefinitionBuilder datasourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(Order.class);
        properties.forEach(datasourceBuilder::addPropertyValue);
        AbstractBeanDefinition beanDefinition = datasourceBuilder.getBeanDefinition();
        registry.registerBeanDefinition("order", beanDefinition);
        System.out.println("BeanDefinitionRegistryPostProcessor--registry bean :order");
    }


    //BeanFactoryPostProcessor 用来在读取所有的beanDefinition信息之后，实例化之前，通过该接口可进一步自行处理，比如修改beanDefinition等
    //调用点在上面第一个扩展接口之后，也在PostProcessorRegistrationDelegate
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        beanFactory.addBeanPostProcessor();
        System.out.println("BeanFactoryPostProcessor--postProcessBeanFactory");
    }


    //用于预测Bean的类型，返回第一个预测成功的Class类型，如果不能预测返回null；
    // 当你调用BeanFactory.getType(name)时当通过bean的名字无法得到bean类型信息时就调用该回调方法来决定类型信息。
    @Override
    public Class<?> predictBeanType(Class<?> aClass, String s) throws BeansException {
//      print(aClass, Thread.currentThread().getStackTrace()[1].getMethodName());
        return aClass;
    }

    //实例化bean之前，相当于new这个bean之前
    //如果接口返回不为null，则不再执行对应 bean的实例化之前流程，直接执行后面的 postProcessAfterInitialization 方法,也就是结束bean实例化流程
    //调用点在 AbstractAutowireCapableBeanFactory
    @Override
    public Object postProcessBeforeInstantiation(Class<?> aClass, String s) throws BeansException {
        print(aClass, Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    //该扩展点决定判断合适的 bean 构造方法。并实例化需要的参数 bean。
    //调用点在 AbstractAutowireCapableBeanFactory 中
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> aClass, String beanName) throws BeansException {
        print(aClass, Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean--afterPropertiesSet");
    }

    //该接口用来合并BeanDefinition，也是对BeanDefinition处理一种扩展接口
    //调用点在AbstractAutowireCapableBeanFactory中
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition rootBeanDefinition, Class<?> aClass, String beanName) {
        print(aClass, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    //实例化bean之后，相当于new这个bean之后
    // 在AbstractAutowireCapableBeanFactory.populateBean()填充方法中会触发。
    //该方法默认返回为true，如果返回 false，则中断populateBean方法，即不再执行属性注入的过程。
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        print(bean.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return true;
    }

    //getEarlyBeanReference方法只要有在 Spring 发生循环依赖时调用。
    // 当bean 创建时，为了防止后续有循环依赖，会提前暴露回调方法，
    // 用于 bean 实例化的后置处理。getEarlyBeanReference方法就是在提前暴露的回调方法中触发。
    //具体调用点在DefaultSingletonBeanRegistry
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        print(bean.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return bean;
    }

    //该方法用于属性注入，在 bean 初始化阶段属性填充时触发。如果返回null 会中断属性填充
    //@Autowired，@Resource 等注解原理基于此方法实现。
    //具体调用点在AbstractAutowireCapableBeanFactory中 populateBean 方法：
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, PropertyDescriptor[] propertyDescriptors, Object bean, String s) throws BeansException {
        print(bean.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return propertyValues;
    }


    //初始化bean之前，相当于把bean注入spring上下文之前
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        print(bean.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserBeanPostProcessor--afterPropertiesSet");
    }

    //初始化bean之后，相当于把bean注入spring上下文之后
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        print(bean.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return bean;
    }

    //触发时机为整个项目启动完毕后，自动执行。如果有多个CommandLineRunner，可以利用@Order来进行排序。
    @Override
    public void run(String... strings) throws Exception {
        User user = applicationContext.getBean(User.class);
        Object orderBean = applicationContext.getBean(Order.class);
        System.out.println("CommandLineRunner--run");
        System.out.println(user);
        System.out.println(orderBean);
    }

    //需要手动调用容器的close方法
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }


    private void print(Class<?> aClass, String methodName) {
        if (aClass.equals(User.class)) {
            System.out.println("===User: " + methodName);
        }
        if (aClass.equals(Order.class)) {
            System.out.println("===Order: " + methodName);
        }
    }

}
