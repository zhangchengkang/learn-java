package com.ksf.socket.bio;


import com.ksf.socket.utils.CalculatorUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

/**
 * @author zhangchengkang
 * @date 2022/6/22 23:20
 */
public class BIOServerHandler implements Runnable {

    private  Socket socket;

    public BIOServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new PrintWriter(this.socket.getOutputStream(), true);

            String body ;
            while (true) {
                body = reader.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("server服务端接收参数 : " + body);
                writer.println(body + " = " + Optional.ofNullable(CalculatorUtil.cal(body)).map(Object::toString).orElse(null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader!=null){
                try {
                    reader.close();

                    if (this.socket!=null){
                        this.socket.close();
                        this.socket=null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (writer!=null){
                writer.close();
            }
        }

    }
}
