package org.dandelion.netty.im.config;

import org.dandelion.netty.common.properties.ImCustomProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TODO system custom configuration
 *
 * @author L
 * @version 1.0
 * @date 2022/5/16 16:34
 */
@Configuration
@EnableConfigurationProperties(value = ImCustomProperties.class)
public class SystemRoutingConfig {


    @Value("${im.zk.root}")
    private String root;

    @Value("${im.zk.adder}")
    private String adder;


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
}
