package com.ksf.factory.objectprovider;

import java.util.List;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/22 14:24
 */
public interface DispatchHandler<T> {

    DispatchFactory.HandlerType getHandlerType();

    void dispatch(List<T> data);
}
