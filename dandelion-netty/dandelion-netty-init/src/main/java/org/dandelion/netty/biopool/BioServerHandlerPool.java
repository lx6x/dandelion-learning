package org.dandelion.netty.biopool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * TODO 线程所要执行的任务
 *
 * @author L
 * @version 1.0
 * @date 2022/4/21 17:01
 */
public class BioServerHandlerPool implements Runnable {

    private Socket socket;

    public BioServerHandlerPool(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 读取客户端传入数据
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true) {
                body = in.readLine();
                if (null == body) {
                    break;
                }
                System.out.println("The time server(Thread:" + Thread.currentThread() + ") receive order:" + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                // 写回客户端
                out.println(currentTime);
            }

        } catch (IOException e) {
            if(in != null){
                try {
                    in.close();
                    in = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                    out = null;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}































