package org.dandelion.socket.example;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author lx6x
 */
public class SocketClient {

    private static final String SERVER_ADDRESS = "localhost"; // 服务器地址
    private static final int SERVER_PORT = 8080; // 服务器端口

    public void connectToServer() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the server at " + SERVER_ADDRESS + ":" + SERVER_PORT);

            Map<String,String> map = new HashMap<>();
            map.put("a","1");
            map.put("b","2");

            String messageToSend = JSONObject.toJSONString(map);
            // 向服务器发送消息
            writer.println(messageToSend);
            System.out.println("Sent: " + messageToSend);

            // 从服务器接收消息
            String serverResponse = reader.readLine();
            System.out.println("Received from server: " + serverResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.connectToServer();
    }
}
