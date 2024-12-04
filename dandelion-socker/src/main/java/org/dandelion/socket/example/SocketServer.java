package org.dandelion.socket.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lx6x
 */
public class SocketServer {
    private static final int PORT = 8080; // 设定服务器监听的端口

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Socket server started on port " + PORT);

            while (true) {
                // 接受客户端连接
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress());

                // 处理客户端的请求
                handleClient(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter writer = new PrintWriter(outputStream, true)
        ) {
            String message;
            // 读取客户端发送的消息
            while ((message = reader.readLine()) != null) {
                System.out.println("Received: " + message);
                // 这里可以做一些处理
                writer.println("Echo: " + message); // 发送回客户端的消息
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.startServer();

    }
}
