package org.dandelion.netty.common.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * TODO custom zk connect message
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/14 17:19
 */
//@PropertySource(value = "classpath:/application.properties")
public class ServerCustomProperties {

    /**
     * netty server port
     */
    private Integer port;


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
