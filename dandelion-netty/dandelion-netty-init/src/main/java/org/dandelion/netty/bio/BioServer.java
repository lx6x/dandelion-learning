package org.dandelion.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO 服务端
 *
 * @author L
 * @version 1.0
 * @date 2022/4/21 17:01
 */
public class BioServer {

    public static void main(String[] args) {

        int port = 8080;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();

                new Thread(new BioServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=serverSocket){
                System.out.println("The time server is close.");
                try {
                    serverSocket.close();
                    serverSocket=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
