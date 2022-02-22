package com.ksf.factory.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/22 14:58
 */
@Component("factoryBeanDemo")
public class FactoryBeanDemo implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return FactoryRealBean.builder()
                .name("factoryRealBean")
                .code("factoryRealBean")
                .desc("getBeanByName返回的是getObject方法返回的bean,getBeanByClass返回的是这个类")
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryRealBean.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
