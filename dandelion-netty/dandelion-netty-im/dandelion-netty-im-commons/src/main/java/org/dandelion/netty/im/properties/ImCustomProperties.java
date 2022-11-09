package org.dandelion.netty.im.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * TODO im system custom properties
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/14 17:05
 */
@ConfigurationProperties(prefix = "im")
//@PropertySource(value = "classpath:/application.properties")
public class ImCustomProperties {

    /**
     * server
     */
    @NestedConfigurationProperty
    private ServerCustomProperties server = new ServerCustomProperties();

    /**
     * zk connect message
     */
    @NestedConfigurationProperty
    private ZkCustomProperties zk = new ZkCustomProperties();

    /**
     * user
     */
    @NestedConfigurationProperty
    private UserCustomProperties user = new UserCustomProperties();

    /**
     * route
     */
    @NestedConfigurationProperty
    private RouteCustomProperties route = new RouteCustomProperties();

    public RouteCustomProperties getRoute() {
        return route;
    }

    public void setRoute(RouteCustomProperties route) {
        this.route = route;
    }

    public UserCustomProperties getUser() {
        return user;
    }

    public void setUser(UserCustomProperties user) {
        this.user = user;
    }

    public ZkCustomProperties getZk() {
        return zk;
    }

    public void setZk(ZkCustomProperties zk) {
        this.zk = zk;
    }

    /**
     * server
     */
    public ServerCustomProperties getServer() {
        return server;
    }

    public void setServer(ServerCustomProperties server) {
        this.server = server;
    }
}
