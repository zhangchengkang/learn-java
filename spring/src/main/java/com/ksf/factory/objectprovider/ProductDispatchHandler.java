package com.ksf.factory.objectprovider;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/22 14:24
 */
@Component
public class ProductDispatchHandler implements DispatchHandler<ProductDispatchHandler.Product> {

    @Override
    public DispatchFactory.HandlerType getHandlerType() {
        return DispatchFactory.HandlerType.Product;
    }

    @Override
    public void dispatch(List<Product> data) {
        System.out.println("下发商品数据:");
        data.forEach(t -> System.out.println(t.toString()));
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class Product {
        private String name;
        private String code;
    }
}
