package org.dandelion.netty.common.properties;

/**
 * TODO
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/16 20:55
 */
public class RouteCustomProperties {

    /**
     * login
     */
    private String loginUrl;

    /**
     * chat
     */
    private String chatUrl;

    /**
     * logout
     */
    private String logoutUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
}
