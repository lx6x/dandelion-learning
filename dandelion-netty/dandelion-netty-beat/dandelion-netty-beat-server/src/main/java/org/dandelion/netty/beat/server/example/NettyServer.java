package org.dandelion.netty.beat.server.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
@Component
public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    // 用于服务端接受客户端连接
    private static final EventLoopGroup parentGroup = new NioEventLoopGroup();
    // 用于进行 SocketChannel 的网路读写
    private static final EventLoopGroup childGroup = new NioEventLoopGroup();

    @Value("${netty.server.port}")
    private Integer serverPort;

    @PostConstruct
    public void start() {
        logger.info("启动 netty server ...");
        // Netty 用于启动 NIO 服务器的辅助启动类
        ServerBootstrap sb = new ServerBootstrap();
        // 将两个 NIO 线程组传入辅助启动类中
        sb.group(parentGroup, childGroup)
                .localAddress(new InetSocketAddress(serverPort))
                // 设置创建的 Channel 类型为 NioServeSocketChannel
                .channel(NioServerSocketChannel.class)
                // 配置 NioServerSocketChannel 的 TCP 参数
                .childOption(ChannelOption.SO_BACKLOG, 1024)
                // 设置绑定 IO 事件的处理类
                .childHandler(new ServerInitializer());
        // 绑定并开始接受传入的连接。
        try {
            ChannelFuture channelFuture = sb.bind().sync();
            if (channelFuture.isSuccess()) {
                logger.info("启动 netty server 成功 ...");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        parentGroup.shutdownGracefully().syncUninterruptibly();
        childGroup.shutdownGracefully().syncUninterruptibly();
        logger.info("关闭 Netty 成功 ...");
    }

}
