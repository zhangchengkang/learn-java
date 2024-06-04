package com.ksf.nettyDemo.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
  
public class ITDragonClientHandler extends ChannelInboundHandlerAdapter{

   //当客户端连接服务器完成就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("====连接服务器完成====");
    }

    //当通道有读取事件时会触发，即服务端发送数据给客户端
    @Override
    public void channelRead(ChannelHandlerContext chc, Object msg) {  
        try {  
        	/* 设置字符串形式的解码 new StringDecoder() 后可以直接使用
            ByteBuf buf = (ByteBuf) msg;  
            byte[] req = new byte[buf.readableBytes()];  
            buf.readBytes(req);  
            String body = new String(req, "utf-8");  
            */
            System.out.println("Netty Client :" + msg);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            ReferenceCountUtil.release(msg);  // 若收到的消息的类型是ByteBuf需要释放，因为它是引用计数对象，这个对象必须显示地调用release()方法来释放。 
        }  
    }  
      
    public void exceptionCaught(ChannelHandlerContext chc, Throwable cause) {  
        cause.printStackTrace();  
        chc.close();  
    }  
  
}  
