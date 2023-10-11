package org.dandelion.netty.im.handler;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * store all connected channels
 *
 * @author lx6x
 * @version 1.0
 * @date 2022/5/18 17:05
 */
public class ChannelMap {

    private static ChannelMap instance;

    /**
     * store all connected channels
     */
    private final Map<String, Channel> connectMap = new ConcurrentHashMap<>();

    private ChannelMap() {
    }

    /**
     * use singleton
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

    public void putClient(String key, Channel channel){
        connectMap.put(key, channel);
    }

    public void remove(String key) {
        connectMap.remove(key);
    }
}
