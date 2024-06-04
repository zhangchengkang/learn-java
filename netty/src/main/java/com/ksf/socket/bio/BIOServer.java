package com.ksf.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangchengkang
 * @date 2022/6/22 23:19
 * <p>
 * * IO 也称为 BIO，Block IO 阻塞同步的通讯方式
 * * 比较传统的技术，实际开发中基本上用Netty或者是AIO。熟悉BIO，NIO，体会其中变化的过程。作为一个web开发人员，stock通讯面试经常问题。
 * * BIO最大的问题是：阻塞，同步。
 * * BIO通讯方式很依赖于网络，若网速不好，阻塞时间会很长。每次请求都由程序执行并返回，这是同步的缺陷。
 * * BIO工作流程：
 * * 第一步：server端服务器启动
 * * 第二步：server端服务器阻塞监听client请求
 * * 第三步：server端服务器接收请求，创建线程实现任务
 */
public class BIOServer {

    private static final Integer PORT = 8888;

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        ThreadPoolExecutor executor = null;
        try {
            server = new ServerSocket(PORT);// ServerSocket 启动监听端口
            System.out.println("BIO Server 服务器启动.....");
            executor = new ThreadPoolExecutor(5, 10, 3600, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));
            while (true) {
                System.out.println("server服务监听中.....");
                socket = server.accept();// 服务器监听：阻塞，等待Client请求
                BIOServerHandler bioServerHandler = new BIOServerHandler(socket);
                executor.execute(bioServerHandler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (server != null) {
                    server.close();
                    System.out.println("BIO Server 服务器关闭！！！！");
                }
                if (executor != null) {
                    executor.shutdown();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
