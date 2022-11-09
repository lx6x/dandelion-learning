package org.dandelion.netty.nio;

/**
 * TODO nio client 测试
 *
 * @author L
 * @version 1.0
 * @date 2022/4/22 10:04
 */
public class NioClient {

    public static void main(String[] args) {
        int port = 8080;
        String ip = "127.0.0.1";
        NioClientHandler nioClientHandler = new NioClientHandler(port, ip);
        new Thread(nioClientHandler, "NIO-NioClient-1").start();
    }
}
