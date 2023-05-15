package org.dandelion.netty.im.config;

import org.dandelion.netty.common.properties.ImCustomProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TODO system custom configuration
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/14 17:03
 */
@Configuration
@EnableConfigurationProperties(value = ImCustomProperties.class)
public class SystemServerConfig {

    @Value("${im.zk.root}")
    private String root;

    @Value("${im.zk.adder}")
    private String adder;

    @Value("${im.server.port}")
    private Integer nettyPort;

    @Value("${server.port}")
    private Integer serverPort;

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public Integer getNettyPort() {
        return nettyPort;
    }

    public void setNettyPort(Integer nettyPort) {
        this.nettyPort = nettyPort;
    }

    @Override
    public String toString() {
        return "SystemConfiguration{" +
            "root='" + root + '\'' +
            ", adder='" + adder + '\'' +
            ", nettyPort=" + nettyPort +
            ", serverPort=" + serverPort +
            '}';
    }
}
