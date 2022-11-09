package org.dandelion.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/4/22 10:05
 */
public class NioClientHandler implements Runnable {

    private int port;
    private String ip;

    private SocketChannel socketChannel;
    private Selector selector;
    private volatile boolean stop;

    public NioClientHandler(int port, String ip) {
        this.port = port;
        this.ip = ip;

        try {
            // client 打开 SocketChannel
            socketChannel = SocketChannel.open();
            // 创建 selector 线程
            selector = Selector.open();
            System.out.println("selector: " + selector);
            // 设置非阻塞模式
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            // 非正常退出，
            System.exit(1);
        }
    }

    @Override
    public void run() {

        try {
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey selectionKey = null;
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(selectionKey);
                    } catch (Exception e) {
                        if(selectionKey!=null){
                            selectionKey.cancel();
                            if(selectionKey.channel()!=null){
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(selector !=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey selectionKey) throws Exception {
        if (selectionKey.isValid()) {
            SocketChannel client = (SocketChannel) selectionKey.channel();
            System.out.println("client: " + client);
            // 测试此密钥的通道是否已完成或未能完成其套接字连接操作
            if (selectionKey.isConnectable()) {
                // 是否完成连接
                if (client.finishConnect()) {
                    client.register(selector, SelectionKey.OP_READ);
                    doWrite(client);
                } else {
                    System.exit(1);
                }
            }
            if (selectionKey.isReadable()) {
                ByteBuffer receivebuffer = ByteBuffer.allocate(1024);
                int count = client.read(receivebuffer);
                if (count > 0) {
                    receivebuffer.flip();
                    // remaining()方法 返回 Byffer 中剩余的可读数据长度
                    byte[] bytes = new byte[receivebuffer.remaining()];
                    receivebuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("服务端写回数据 Now is " + body);
                    this.stop = true;
                } else if (count < 0) {
                    selectionKey.channel();
                    client.close();
                }
            }
        }
    }

    private void doConnect() throws IOException, InterruptedException {
        // 连接服务端
        boolean connect = socketChannel.connect(new InetSocketAddress(ip, port));
        // 判断是否连接成功，如果连接成功，则监听 channel 的读状态
        if (connect) {
            // 注册监听
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("socketChannel: " + socketChannel);
            // 写数据，写给服务端
            doWrite(socketChannel);
        } else {
            // 如果没有连接成功，则向多路复用器注册 connect 状态
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            System.out.println("socketChannel: " + socketChannel);
        }
    }

    private void doWrite(SocketChannel channel) throws InterruptedException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("QUERY TIME ORDER".getBytes());
        // 翻转此缓冲区。限制设置为当前位置，然后位置设置为零。如果标记已定义，则将其丢弃。
        // 个人理解为刷新
        buffer.flip();
        Thread.sleep(2000);
        // 向 channel 中写入客户端的请求命令，写到服务端
        channel.write(buffer);
        // 当前位置和限制之间是否有任何元素。
        if (!buffer.hasRemaining()) {
            System.out.println("Send order to server succeed.");
        }
    }
}
