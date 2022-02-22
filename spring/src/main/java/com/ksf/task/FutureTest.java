package com.ksf.task;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: zhangchengkang
 * @Date: 2022/2/17 15:33
 */
public class FutureTest {

    /**
     * 不指定线程池  会使用默认的ForkJoinPool 核心线程数 2*核心数
     *
     * supplyAsync 创建带返回值的异步任务 executor.submit
     * runAsync  创建无返回值的异步任务  executor.execute
     */
    public static void main(String[] args) throws Exception {
        Random random = new Random();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> random.nextInt(10));
        System.out.println("单个future: " + future.get());

        //whenCompleteAsync  可能用其他线程去执行
        CompletableFuture<Integer> whenComplete = future.whenComplete((v, e) -> System.out.println("return value:" + v + "  exception:" + e));
        System.out.println("whenComplete: " + whenComplete.get());

        CompletableFuture<String> thenApply = whenComplete.thenApply(t -> t + "thenApply ").thenApply(t -> t + "thenApply ");
        System.out.println("thenApply: " + thenApply.get());

        CompletableFuture<Void> thenAccept = thenApply.thenAccept(t -> System.out.println(t + " then accept"));
        System.out.println("thenAccept: " + thenAccept.get());

        //异步转同步
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> random.nextInt(10)));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<Integer> t : futures) {
            System.out.println("异步转同步: " + t.get());
        }

//        ListeningExecutorService singleThreadExecutor = (ListeningExecutorService) Executors.newSingleThreadExecutor();
//        ListenableFuture<Integer> listenableFuture =  singleThreadExecutor.submit(() -> random.nextInt(10));
//        listenableFuture.addCallback(result -> System.out.println("listenableFuture:" + result), ex -> System.out.println("ex"));


    }

}
