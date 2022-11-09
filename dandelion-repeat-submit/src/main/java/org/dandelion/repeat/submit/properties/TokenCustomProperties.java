package org.dandelion.repeat.submit.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO token
 *
 * @author L
 * @version 1.0
 * @date 2022/06/15 17:26
 */
@ConfigurationProperties(prefix = "token")
public class TokenCustomProperties {

    /**
     * 自定义请求头
     */
    private String header;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
