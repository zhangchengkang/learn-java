
### spring三级缓存
- 一级缓存singletonObjects：完全实例化好的bean map
- 二级缓存earlySingletonObjects：只分配空间(初始化)的bean map  跟一级缓存区分 怕拿到没实例化好的对象  引发空指针
- 三级缓存singletonFactories： bean Factory map  解决aop下的循环依赖  因为aop之后会生成代理对象  但是一级缓存返回的是实际对象  加入singletonFactories三级缓存的前提是执行了构造器，所以构造器的循环依赖没法解
- 如果要使用二级缓存解决循环依赖，意味着所有Bean在实例化后就要完成AOP代理，这样违背了Spring设计的原则，Spring在设计之初就是通过 BeanPostProcess 这个后置处理器来在Bean生命周期的最后一步来完成AOP代理，而不是在实例化后就立马进行AOP代理。

### ioc初始化过程
- 加载配置文件并解析
- 将这些信息封装在 BeanDefinition 类
- 通过 BeanDefinitionRegistry 接口将这些信息 注册 起来，放在 beanDefinitionMap 变量中
- 将抽象好的BeanDefinition注册到IoC容器中

### 实例化bean
- 填充属性值及引用
- 调用 BeanNameAware 的 setBeanName(String name) 设置 bean 的 id
- 调用 BeanFactoryAware 的 setBeanFactory(BeanFactory beanFactory) 设置 BeanFactory Bean工厂
- 调用ApplicationContextAware 的 setApplicationContext(ApplicationContext applicationContext)
- 如果实现 BeanPostProcessor，则 调用 postProcessBeforeInitialization() 初始化前的后置处理方法
- 如果实现了 InitializingBean 接口，则使用 afterPropertiesSet() 来初始化属性
- 执行  @PostConstruct
- 如果实现 BeanPostProcessor，则 调用 postProcessAfterInitialization() 初始化后的后置处理方法
- 此时，bean 就可以使用了
- DisposableBean接口 destroy() 销毁bean。不过在Spring5.0开始

### Spring MVC 初始化
- 用户向服务器发送请求，请求被SpringMVC的前端控制器DispatcherServlet拦截
- DispatcherServlet对请求URL进行解析，调用HandlerMapping获得该Handler配置的所有相关对象，包括Handler对象以及Handler对象对应的拦截器，这些对象被封装到一个HandlerExecutionchain当中返回
- DispatcherServlet根据获得的Handler（Controller），选择一个合适的HandlerAdapter。一个HandlerAdapter会被用于处理多种（一类）Handler，并调用Handler实际处理请求的方法。
- 在调用Handler实际处理请求的方法之前，HandlerAdapter首先会结合用户配置对请求消息进行转换（序列化），然后通过DataBinder将请求中的模型数据绑定到Handler对应的处理方法的参数中。
- Handler调用业务逻辑组件完成对请求的处理后，向DispatcherServlet返回一个ModelAndView对象，ModelAndView对象中应该包含视图名或者视图和模型。
- DispatcherServlet调用视图解析器ViewResolver结合Model来渲染视图
- DispatcherServlet将视图渲染结果返回给客户端

### SpringBoot 启动流程
- @EnableAutoConfiguration
- @Import(AutoConfigurationImportSelector.class)
- AutoConfigurationImportSelector 类实现了 ImportSelector接口
- 扫描并加载jar包下的META-INF/spring.factories文件的配置类
- 将configuration类中定义的bean加入spring到容器中























### springboot
https://blog.csdn.net/woshilijiuyi/article/details/85396492
https://www.cnblogs.com/grey-wolf/p/13034371.html

### spingmybatis
https://blog.csdn.net/qq_41907991/category_9907747.html

### JUC
https://gitee.com/moxi159753/LearningNotes/tree/master/%E6%A0%A1%E6%8B%9B%E9%9D%A2%E8%AF%95/JUC

TransmittableThreadLocal线程池
InternalThreadLocal
