package org.dandelion.netty.beat.server.example;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class ConnectionListener implements ChannelFutureListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionListener.class);

    private final NettyClient nettyClient;

    ConnectionListener(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            logger.info("启动 netty client 成功 ...");
        } else {
            logger.info("重新连接 netty server ...");
            EventLoop eventExecutors = future.channel().eventLoop();
            eventExecutors.schedule(new Runnable() {
                @Override
                public void run() {
                    nettyClient.start();
                }
            }, 1L, TimeUnit.SECONDS);
        }
    }
}
