package com.ksf.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/17 17:30
 */
public class BlockingQueueTest {

    /**
     * * ArrayBlockQueue：由数组结构组成的有界阻塞队列
     * * LinkedBlockingQueue：由链表结构组成的有界（默认大小 Integer.MAX_VALUE）的阻塞队列  有界，但是界限非常大，相当于无界，可以当成无界
     * * PriorityBlockQueue：支持优先级排序的无界阻塞队列
     * * DelayQueue：使用优先级队列实现的延迟无界阻塞队列
     * * SynchronousQueue：不存储元素的阻塞队列，也即单个元素的队列 生产一个，消费一个，不存储元素，不消费不生产  每一个put操作必须等待一个take操作，否者不能继续添加元素
     * * LinkedTransferQueue：由链表结构组成的无界阻塞队列
     * * LinkedBlockingDeque：由链表结构组成的双向阻塞队列
     */
    public static void main(String[] args) throws Exception{

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        //remove 当阻塞队列空时：再往队列中remove移除元素，会抛出NoSuchException
//        blockingQueue.remove();

        //add 当阻塞队列满时：在往队列中add插入元素会抛出 IllegalStateException：Queue full
//        System.out.println(blockingQueue.add("a"));
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("XXX"));

        //offer 插入方法，成功true，失败false
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("d"));

        //poll 移除方法：成功返回出队列元素，队列没有就返回null
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());

        //put 当阻塞队列满时，生产者继续往队列里put元素，队列会一直阻塞生产线程直到put数据or响应中断退出，
//        blockingQueue.put("a");
//        blockingQueue.put("b");
//        blockingQueue.put("c");
//        blockingQueue.put("d");

        //take 当阻塞队列空时，消费者线程试图从队列里take元素，队列会一直阻塞消费者线程直到队列可用。
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();

        //offer( ) ， poll() 加时间
        //使用offer插入的时候，需要指定时间，如果2秒还没有插入，那么就放弃插入
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d", 2L, TimeUnit.SECONDS));
        //poll取的时候也一样
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));

    }
}
