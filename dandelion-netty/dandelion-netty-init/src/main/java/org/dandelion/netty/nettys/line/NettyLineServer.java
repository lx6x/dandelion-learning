package org.dandelion.netty.nettys.line;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * TODO netty server line strategy test
 *
 * @author L
 * @version 1.0
 * @date 2022/4/27 16:06
 */
public class NettyLineServer {

    public static void main(String[] args) {
        // 定义服务端端口
        int port = 8080;
        new NettyLineServer().bind(port);
    }

    private void bind(int port) {
        // 服务端需要有两个 EventLoopGroup 一个用于处理连接，一个用于处理事件
        // 用于服务端接受客户端连接
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        // 用于进行 SocketChannel 的网路读写
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try { // Netty 用于启动 NIO 服务器的辅助启动类
            ServerBootstrap sb = new ServerBootstrap();
            // 将两个 NIO 线程组传入辅助启动类中
            sb.group(parentGroup, childGroup)
                    // 设置创建的 Channel 类型为 NioServeSocketChannel
                    .channel(NioServerSocketChannel.class)
                    // 配置 NioServerSocketChannel 的 TCP 参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 设置绑定 IO 事件的处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 创建 NioSocketChannel 成功后，在进行初始化时，将它的 ChannelHandler 设置到 ChannelPipeline 中，用于处理网络 IO 事件
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 处理粘包拆包问题
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            // 处理 IO
                            socketChannel.pipeline().addLast(new NettyLineServerHandler());
                        }
                    });

            // 绑定端口，同步等待成功（sync：同步阻塞方法，等待 bind 操作完成才继续）
            // ChannelFuture 用于异步操作的通知等待
            ChannelFuture channelFuture = sb.bind(port).sync();
            System.out.println("Netty Server Start Port 8080");
            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}



















