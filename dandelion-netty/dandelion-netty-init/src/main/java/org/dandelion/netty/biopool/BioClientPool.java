package org.dandelion.netty.biopool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TODO 客户端
 *
 * @author L
 * @version 1.0
 * @date 2022/4/21 17:01
 */
public class BioClientPool {

    public static void main(String[] args) {
        int port = 8080; //服务端默认端口
        int i = 10;
        while (i > 1) {
            System.out.println("-----------" + i + "-----------");
            Socket socket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                socket = new Socket("127.0.0.1", port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                //传给服务端的指令
                out.println("QUERY TIME ORDER");
                System.out.println("Send order to server succeed.");
                String resp = in.readLine();
                System.out.println("Now is : " + resp);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    socket = null;
                }
            }

            i--;
        }

    }
}
