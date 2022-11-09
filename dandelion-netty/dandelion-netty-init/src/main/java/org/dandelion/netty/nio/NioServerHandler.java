package org.dandelion.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/4/22 10:05
 */
public class NioServerHandler implements Runnable {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private volatile boolean stop;

    public NioServerHandler(int port) {
        try {
            // server 打开 ServerSockerChannel
            serverSocketChannel = ServerSocketChannel.open();
            // 设置非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 绑定监听的端口地址
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 2);
            // 创建 Selector 线程
            selector = Selector.open(); 
            System.out.println("创建 selector: " + selector);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("时间服务器在端口启动: " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                // 通过 selector 循环准备就绪的 channel
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey selectionKey = null;
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    // 删除已处理
                    iterator.remove();
                    try {
                        handleInput(selectionKey);
                    } catch (IOException e) {
                        if (selectionKey != null) {
                            selectionKey.cancel();
                            if (selectionKey.channel() != null) {
                                selectionKey.channel().close();
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey selectionKey) throws IOException {
        // key 是否有效
        boolean valid = selectionKey.isValid();
        if (valid) {
            // 测试此键的通道是否准备好接受新的套接字连接
            boolean acceptable = selectionKey.isAcceptable();
            if (acceptable) {
                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                // 多路复用器监听到新的客户端连接，处理连接请求，完成TCP三次握手
                // 接受与此通道的套接字建立的连接
                SocketChannel client = channel.accept();
                System.out.println("socketChannel: " + client);
                // 设置非阻塞模式
                client.configureBlocking(false);
                // 将新的连接注册到多路复用器上，监听其读操作，读取客户端发送的消息
                // OP_READ：读操作的操作设置位
                client.register(selector, SelectionKey.OP_READ);
            }
            // 测试此键的通道是否已准备好读取
            boolean readable = selectionKey.isReadable();
            if (readable) {
                SocketChannel client = (SocketChannel) selectionKey.channel();
                ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
                // 读取客户端请求信息到缓冲区
                int content = client.read(receiveBuffer);
                if (content > 0) {
                    receiveBuffer.flip();
                    // remaining()方法  返回 Byffer 中剩余的可读数据长度
                    byte[] bytes = new byte[receiveBuffer.remaining()];
                    // 从缓存区读取信息
                    receiveBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server(Thread:" + Thread.currentThread() + ") receive order : " + body);
                    // 将 currentTime 响应给客户端 （客户端的 channel ）
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(client, currentTime);
                } else if (content < 0) {
                    selectionKey.cancel();
                    client.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel client, String currentTime) throws IOException {
        if (null != currentTime && currentTime.trim().length() > 0) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(currentTime.getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            // 将客户端响应消息写入到客户端 channel 中
            client.write(buffer);
            System.out.println("服务端发送数据至客户端: " + currentTime);
        } else {
            System.out.println("没有数据写回");
        }
    }

}
