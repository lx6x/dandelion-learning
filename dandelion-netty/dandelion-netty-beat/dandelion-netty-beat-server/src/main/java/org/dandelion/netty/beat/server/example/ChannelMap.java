package org.dandelion.netty.beat.server.example;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujunfei
 * @date 2023/5/16
 */
public class ChannelMap {

    private static ChannelMap instance;

    /**
     * 存储所有连接的通道
     */
    private final Map<String, Channel> connectMap = new ConcurrentHashMap<>();

    private ChannelMap() {
    }

    /**
     * 使用单例
     *
     * @return this
     * @author L
     */
    public static ChannelMap newInstance() {
        if (null == instance) {
            instance = new ChannelMap();
        }
        return instance;
    }

    public Map<String, Channel> getConnectMap() {
        return connectMap;
    }

    public void putClient(String userId, Channel channel){
        connectMap.put(userId, channel);
    }
}
