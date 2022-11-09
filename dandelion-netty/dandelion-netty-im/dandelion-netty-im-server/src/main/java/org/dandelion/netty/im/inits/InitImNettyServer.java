package org.dandelion.netty.im.inits;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.dandelion.netty.im.config.SystemServerConfig;
import org.dandelion.netty.im.hadle.ImServerHandle;
import org.dandelion.netty.im.protocol.MessageProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * TODO init im netty server
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/15 15:58
 */
@Component
@Order(3)
public class InitImNettyServer {

    private static final Logger logger = LoggerFactory.getLogger(InitImNettyServer.class);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Autowired
    private SystemServerConfig systemServerConfig;


    @PostConstruct
    private void startNetty() {
        logger.info("----> im netty server start begin");

        // auxiliary startup class used by netty to start the NIO server
        ServerBootstrap sb = new ServerBootstrap();
        // pass two NIO thread groups into the auxiliary startup class
        sb.group(bossGroup, workerGroup)
                // set the created channel to the NioServerSocketChannel type
                .channel(NioServerSocketChannel.class)
                // keep long connection
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // set the handler class for binding IO events
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        // google Protobuf 编解码
                        channelPipeline.addLast(new ProtobufVarint32FrameDecoder());
                        channelPipeline.addLast(new ProtobufDecoder(MessageProto.MessageProtocol.getDefaultInstance()));
                        channelPipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        channelPipeline.addLast(new ProtobufEncoder());
//                        channelPipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
                        // message processing
                        channelPipeline.addLast(new ImServerHandle());
                    }
                });
        try {
            Integer nettyPort = systemServerConfig.getNettyPort();
            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = sb.bind(nettyPort).sync();
            if (channelFuture.isSuccess()) {
                logger.info("----> server start success, port: {}", nettyPort);
            }

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
            logger.error("----> server start fail");
        }

    }


}
