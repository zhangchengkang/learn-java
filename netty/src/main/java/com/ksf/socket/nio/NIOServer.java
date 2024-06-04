package com.ksf.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author zhangchengkang
 * @date 2022/6/23 00:43
 * <p>
 * *
 * * * NIO 也称 New IO， Non-Block IO，非阻塞同步通信方式
 * *  * 从BIO的阻塞到NIO的非阻塞，这是一大进步。功归于Buffer，Channel，Selector三个设计实现。
 * *  * Buffer   ： 缓冲区。NIO的数据操作都是在缓冲区中进行。缓冲区实际上是一个数组。而BIO是将数据直接写入或读取到Stream对象。
 * *  * Channel  ：  通道。NIO可以通过Channel进行数据的读，写和同时读写操作。
 * *  * Selector ：  多路复用器。NIO编程的基础。多路复用器提供选择已经就绪状态任务的能力。
 * *  *
 * *  * NIO过人之处：
 * *  * 客户端和服务器通过Channel连接，而这些Channel都要注册在Selector。Selector通过一个线程不停的轮询这些Channel。找出已经准备就绪的Channel执行IO操作。
 * *  * NIO通过一个线程轮询，实现千万个客户端的请求，这就是非阻塞NIO的特点。
 */
public class NIOServer{

    private static final int PORT = 8888; 		 // 监听的端口

    public static void main(String[] args) {
        try {
            // 1.开启多路复用器
            Selector selector = Selector.open();
            // 2.打开服务器通道(网络读写通道)
            ServerSocketChannel channel = ServerSocketChannel.open();
            // 3.设置服务器通道为非阻塞模式，true为阻塞，false为非阻塞
            channel.configureBlocking(false);
            // 4.绑定端口
            channel.socket().bind(new InetSocketAddress(PORT));
            // 5.把通道注册到多路复用器上，并监听阻塞事件
            /**
             * SelectionKey.OP_READ 	: 表示关注读数据就绪事件
             * SelectionKey.OP_WRITE 	: 表示关注写数据就绪事件
             * SelectionKey.OP_CONNECT: 表示关注socket channel的连接完成事件
             * SelectionKey.OP_ACCEPT : 表示关注server-socket channel的accept事件
             */
            channel.register(selector, SelectionKey.OP_ACCEPT);

            NIOServerHandler nioServerHandler = new NIOServerHandler(selector);
            new Thread(nioServerHandler).start();
            System.out.println("Server start >>>>>>>>> port :" + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
