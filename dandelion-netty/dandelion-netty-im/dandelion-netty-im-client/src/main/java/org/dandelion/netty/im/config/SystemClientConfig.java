package org.dandelion.netty.im.config;

import org.dandelion.netty.im.properties.ImCustomProperties;
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
public class SystemClientConfig {

    @Value("${im.user.id}")
    private String userId;
    @Value("${im.user.name}")
    private String userName;
    @Value("${im.route.login-url}")
    private String routeLoginUrl;
    @Value("${im.route.chat-url}")
    private String routeChatUrl;
    @Value("${im.route.logout-url}")
    private String routeLogoutUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRouteLoginUrl() {
        return routeLoginUrl;
    }

    public void setRouteLoginUrl(String routeLoginUrl) {
        this.routeLoginUrl = routeLoginUrl;
    }

    public String getRouteChatUrl() {
        return routeChatUrl;
    }

    public void setRouteChatUrl(String routeChatUrl) {
        this.routeChatUrl = routeChatUrl;
    }

    public String getRouteLogoutUrl() {
        return routeLogoutUrl;
    }

    public void setRouteLogoutUrl(String routeLogoutUrl) {
        this.routeLogoutUrl = routeLogoutUrl;
    }
}
