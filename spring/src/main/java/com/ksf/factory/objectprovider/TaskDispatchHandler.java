package com.ksf.factory.objectprovider;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/22 14:25
 */
@Component
public class TaskDispatchHandler implements DispatchHandler<TaskDispatchHandler.Task> {

    @Override
    public DispatchFactory.HandlerType getHandlerType() {
        return DispatchFactory.HandlerType.Task;
    }

    @Override
    public void dispatch(List<Task> data) {
        System.out.println("下发任务数据:");
        data.forEach(t -> System.out.println(t.toString()));
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class Task {
        private String code;
        private String name;
    }
}
