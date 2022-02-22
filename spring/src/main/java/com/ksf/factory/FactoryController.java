package com.ksf.factory;

import com.ksf.event.EventDemo;
import com.ksf.factory.factorybean.FactoryBeanDemo;
import com.ksf.factory.objectprovider.DispatchFactory;
import com.ksf.factory.objectprovider.DispatchHandler;
import com.ksf.factory.objectprovider.ProductDispatchHandler;
import com.ksf.factory.objectprovider.ProductDispatchHandler.Product;
import com.ksf.factory.objectprovider.TaskDispatchHandler;
import com.ksf.factory.objectprovider.TaskDispatchHandler.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/22 14:40
 */
@RestController
public class FactoryController {

    @Autowired
    private DispatchFactory dispatchFactory;

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping("/task/dispatch")
    public String taskDispatch() {
        DispatchHandler handler = dispatchFactory.getHandler(DispatchFactory.HandlerType.Task);
        List<Task> list = new ArrayList<>();
        list.add(Task.builder().code("task1").name("任务1").build());
        list.add(Task.builder().code("task2").name("任务2").build());
        handler.dispatch(list);
        return "下发成功";
    }

    @RequestMapping("/product/dispatch")
    public String productDispatch() {
        DispatchHandler handler = dispatchFactory.getHandler(DispatchFactory.HandlerType.Product);
        List<Product> list = new ArrayList<>();
        list.add(Product.builder().code("231231").name("奥利奥").build());
        list.add(Product.builder().code("343231").name("电蚊香").build());
        handler.dispatch(list);
        return "下发成功";
    }

    @RequestMapping("/factory/bean")
    public String getFactoryBean() throws Exception {
        FactoryBeanDemo bean = applicationContext.getBean(FactoryBeanDemo.class);
        System.out.println("FactoryBeanDemo getBean objectType: " + bean.getObjectType());
        System.out.println("FactoryBeanDemo: getObject" + bean.getObject().toString());

        Object factoryBeanDemo = applicationContext.getBean("factoryBeanDemo");
        System.out.println("factoryBeanDemo:" + factoryBeanDemo.toString());
        return "成功";
    }
}
