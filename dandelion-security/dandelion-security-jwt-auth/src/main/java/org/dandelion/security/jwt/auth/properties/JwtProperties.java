package org.dandelion.security.jwt.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/10 16:12
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT加解密使用的密钥
     */
    private String secret;

    /**
     * WT的超期限时间(60*60*24)
     */
    private Integer expiration;

    /**
     * JWT存储的请求头
     */
    private String tokenHeader;

    /**
     * JWT负载中拿到开头
     */
    private String tokenHead;

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}
