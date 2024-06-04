package com.ksf.socket.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author zhangchengkang
 * @date 2022/6/23 01:34
 */
public class NIOServerHandler implements Runnable{

    private final int BUFFER_SIZE = 1024; // 缓冲区大小
    private Selector selector;  			// 多路复用器，NIO编程的基础，负责管理通道Channel
    // 缓冲区Buffer，和BIO的一个重要区别（NIO读写数据是在缓冲区中进行，而BIO是通过流的形式）
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public NIOServerHandler(Selector selector){
        this.selector = selector;
    }

    @Override
    public void run() {
        while (true) {
            try {
                /**
                 * a.select() 阻塞到至少有一个通道在你注册的事件上就绪
                 * b.select(long timeOut) 阻塞到至少有一个通道在你注册的事件上就绪或者超时timeOut
                 * c.selectNow() 立即返回。如果没有就绪的通道则返回0
                 * select方法的返回值表示就绪通道的个数。
                 */
                // 1.多路复用器监听阻塞
                selector.select();
                // 2.多路复用器已经选择的结果集
                Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                // 3.不停的轮询
                while (selectionKeys.hasNext()) {
                    // 4.获取一个选中的key
                    SelectionKey key = selectionKeys.next();
                    // 5.获取后便将其从容器中移除
                    selectionKeys.remove();
                    // 6.只获取有效的key
                    if (!key.isValid()){
                        continue;
                    }
                    // 阻塞状态处理
                    if (key.isAcceptable()){
                        accept(key);
                    }
                    // 可读状态处理
                    if (key.isReadable()){
                        read(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 设置阻塞，等待Client请求。在传统IO编程中，用的是ServerSocket和Socket。在NIO中采用的ServerSocketChannel和SocketChannel
    private void accept(SelectionKey selectionKey) {
        try {
            // 1.获取通道服务
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            // 2.执行阻塞方法
            SocketChannel socketChannel = serverSocketChannel.accept();
            // 3.设置服务器通道为非阻塞模式，true为阻塞，false为非阻塞
            socketChannel.configureBlocking(false);
            // 4.把通道注册到多路复用器上，并设置读取标识
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey selectionKey) {
        try {
            // 1.清空缓冲区数据
            readBuffer.clear();
            // 2.获取在多路复用器上注册的通道
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            // 3.读取数据，返回
            int count = socketChannel.read(readBuffer);
            // 4.返回内容为-1 表示没有数据
            if (-1 == count) {
                selectionKey.channel().close();
                selectionKey.cancel();
                return ;
            }
            // 5.有数据则在读取数据前进行复位操作
            readBuffer.flip();
            // 6.根据缓冲区大小创建一个相应大小的bytes数组，用来获取值
            byte[] bytes = new byte[readBuffer.remaining()];
            // 7.接收缓冲区数据
            readBuffer.get(bytes);
            // 8.打印获取到的数据
            System.out.println("NIO Server : " + new String(bytes)); // 不能用bytes.toString()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
