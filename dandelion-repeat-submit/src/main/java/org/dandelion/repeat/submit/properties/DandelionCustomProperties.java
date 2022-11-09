package org.dandelion.repeat.submit.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO dandelion 系统内置
 *
 * @author L
 * @version 1.0
 * @date 2022/06/14 17:55
 */
@ConfigurationProperties(prefix = "dandelion")
public class DandelionCustomProperties {
    /**
     * 版本
     */
    private String version;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 邮箱
     */
    private String email;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
