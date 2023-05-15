package org.dandelion.netty.im.inits;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.dandelion.netty.common.bean.ChatInfo;
import org.dandelion.netty.common.bean.ServerInfo;
import org.dandelion.netty.common.bean.UserInfo;
import org.dandelion.netty.im.config.SystemClientConfig;
import org.dandelion.netty.common.constant.MessageConstant;
import org.dandelion.netty.im.handle.ImClientHandle;
import org.dandelion.netty.common.protocol.MessageProto;
import org.dandelion.netty.common.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * TODO
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/16 20:20
 */
@Component
public class InitImNettyClient {

    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor executor;

    private static final Logger logger = LoggerFactory.getLogger(InitImNettyClient.class);

    @Autowired
    private SystemClientConfig systemClientConfig;

    private ServerInfo serverInfo;

    private Channel channel;

    private int restart=0;


    @PostConstruct
    public void start() {
        if (null != serverInfo) {
            logger.warn("----> service connected");
            return;
        }
        this.serverInfo = getServerInfo();
        logger.info("----> get ServerInfo information from the routing service: {}", this.serverInfo);
        this.startClient(serverInfo);
        registerServer();
    }

    /**
     * start client create connect
     *
     * @param serverInfo server connect
     * @author L
     * @date 2022/05/16
     */
    private void startClient(ServerInfo serverInfo) {
        logger.info("----> start client begin ...");

        EventLoopGroup group = new NioEventLoopGroup(executor);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline channelPipeline = ch.pipeline();
                    // google Protobuf 编解码
                    channelPipeline.addLast(new ProtobufVarint32FrameDecoder());
                    channelPipeline.addLast(new ProtobufDecoder(MessageProto.MessageProtocol.getDefaultInstance()));
                    channelPipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                    channelPipeline.addLast(new ProtobufEncoder());
//                    channelPipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
                    // message processing
                    channelPipeline.addLast(new ImClientHandle());
                }
            });

        try {
            ChannelFuture channelFuture = bootstrap.connect(serverInfo.getIp(), serverInfo.getNettyPort()).sync();
            if (channelFuture.isSuccess()) {
                logger.info("----> client start success, port: {}", serverInfo.getNettyPort());
            }
            channel = channelFuture.channel();
        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
            logger.error("----> client start fail");
        }


        logger.info("----> start client finish ...");
    }


    /**
     * get ServerInfo information from the routing service
     *
     * @return void
     * @author L
     * @date 2022/05/16
     */
    private ServerInfo getServerInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(systemClientConfig.getUserId());
        userInfo.setUserName(systemClientConfig.getUserName());
        String string = JSONObject.toJSONString(userInfo);
        String post = HttpUtils.post(systemClientConfig.getRouteLoginUrl(), string);
        if (null == post || "".equals(post)) {
            logger.warn("no server start ...");
            try {
                Thread.sleep(10000);
                if(restart<5){
                    restart++;
                    return getServerInfo();
                }else {
                    throw new RuntimeException("connect server time out");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return JSONObject.parseObject(post, ServerInfo.class);
    }

    /**
     * send message route
     *
     * @param chatInfo chat
     * @author L
     */
    public void sendMessage(ChatInfo chatInfo) {
        String string = JSONObject.toJSONString(chatInfo);
        logger.info("send message: {}", string);
        // send directly to route , route for server distribution
        HttpUtils.post(systemClientConfig.getRouteChatUrl(), string);
    }

    /**
     * connect to server and send initial message
     *
     * @author L
     * @date 2022/05/18
     */
    private void registerServer() {
        MessageProto.MessageProtocol messageProtocol = MessageProto.MessageProtocol.newBuilder()
            .setCommand(MessageConstant.LOGIN)
            .setUserId(systemClientConfig.getUserId())
            .setTime(System.currentTimeMillis())
            .setContent("client register server")
            .build();
        channel.writeAndFlush(messageProtocol);
    }
}
