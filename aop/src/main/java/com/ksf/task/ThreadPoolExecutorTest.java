package com.ksf.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.Future;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/17 14:27
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws Exception {
        int n = Runtime.getRuntime().availableProcessors();//获取到服务器的cpu内核
        System.out.println("服务器的cpu内核:{}" + n);
//        threadPoolTaskExecutor();
//        threadPoolExecutor();

        Executors.newFixedThreadPool(3);//核心自己传,核心线程=最大线程,LinkedBlockingQueue
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();//一个核心,一个最大,LinkedBlockingQueue
        Executors.newCachedThreadPool();//0核心 无限最大,SynchronousQueue

        //submit有返回值 execute无返回值
        Future<Integer> future = singleThreadExecutor.submit(() -> {
            Thread.sleep(2000);
            return new Random().nextInt(10);
        });
        while (true) {
            if (future.isDone()) {
                System.out.println("random:" + future.get());
                break;
            }
        }

        singleThreadExecutor.execute(() -> new Random().nextInt(10));
        System.out.println("===end===");
    }


    /**
     * 核心线程-队列-最大线程-拒绝策略
     * <p>
     * 1.AbortPolicy：丢弃任务，并且抛出RejectedExecutionException异常
     * * 2.DiscardPolicy：丢弃任务，不处理，不抛出异常
     * * 3.CallerRunsPolicy：直接在execute方法的调用线程中运行被拒绝的任务
     * * 4.DiscardOldestPolicy：丢弃队列中最前面的任务，然后重新尝试执行任务
     * <p>
     * ArrayBlockQueue：由数组结构组成的有界阻塞队列
     * LinkedBlockingQueue：由链表结构组成的有界（默认大小 Integer.MAX_VALUE）的阻塞队列  有界，但是界限非常大，相当于无界，可以当成无界
     * PriorityBlockQueue：支持优先级排序的无界阻塞队列
     * DelayQueue：使用优先级队列实现的延迟无界阻塞队列
     * SynchronousQueue：不存储元素的阻塞队列，也即单个元素的队列 生产一个，消费一个，不存储元素，不消费不生产
     * LinkedTransferQueue：由链表结构组成的无界阻塞队列
     * LinkedBlockingDeque：由链表结构组成的双向阻塞队列
     */
    public static void threadPoolExecutor() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 60,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 工作开始！");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " 工作结束！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(10000);
        System.out.println("主线程工作结束！");
//        executor.shutdown();
    }

    public static void threadPoolTaskExecutor() throws Exception {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); //核心池大小
        executor.setMaxPoolSize(10); //最大线程数
        executor.setQueueCapacity(10); //队列程度
        executor.setKeepAliveSeconds(60); //线程空闲时间
        executor.setThreadNamePrefix("子线程-");//线程前缀名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); //配置拒绝策略
        executor.initialize(); //初始化

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 工作开始！");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + " 工作结束！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(5000);
        System.out.println("主线程工作结束！");
//        executor.shutdown();
    }
}
