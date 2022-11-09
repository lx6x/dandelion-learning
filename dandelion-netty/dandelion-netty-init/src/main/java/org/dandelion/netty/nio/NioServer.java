package org.dandelion.netty.nio;

/**
 * TODO nio server 测试
 *
 * @author L
 * @version 1.0
 * @date 2022/4/22 10:04
 */
public class NioServer {

    public static void main(String[] args) {
        int port=8080;

        NioServerHandler nioServerHandler=new NioServerHandler(port);

        new Thread(nioServerHandler,"NIO-MultiplexerTimeServer-1").start();
    }
}
