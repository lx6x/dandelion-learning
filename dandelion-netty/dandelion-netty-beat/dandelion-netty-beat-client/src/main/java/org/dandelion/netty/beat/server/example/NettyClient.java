package org.dandelion.netty.beat.server.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
@Component
public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    // 配置客户端 NIO 线程组
    private static final EventLoopGroup clientGroup = new NioEventLoopGroup();

    @Value("${netty.server.port}")
    private Integer serverPort;
    @Value("${netty.server.ip}")
    private String serverIp;

    private SocketChannel socketChannel;
    @PostConstruct
    public void start() {
        logger.info("启动 netty client ...");
        Bootstrap bs = new Bootstrap();
        bs.group(clientGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientHandleInitializer());

        try {
            ChannelFuture channelFuture = bs.connect(serverIp, serverPort).sync();
            if (channelFuture.isSuccess()) {
                logger.info("启动 netty client 成功 ...");
            }
            socketChannel = (SocketChannel) channelFuture.channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
