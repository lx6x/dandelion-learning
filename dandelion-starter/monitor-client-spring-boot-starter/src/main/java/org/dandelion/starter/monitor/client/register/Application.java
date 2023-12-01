package org.dandelion.starter.monitor.client.register;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;

/**
 * 参考 SpringBoot Admin
 * 注册信息
 *
 * @author lx6x
 * @date 2023/11/29
 */
@Data
@ToString(exclude = "metadata")
public class Application {

    private final String name;

    private final String host;

    private final String pid;

    private final String managementUrl;

    private final String healthUrl;

    private final String serviceUrl;

    private final Map<String, String> metadata;

    @Builder
    protected Application(String name, String host, String pid, String managementUrl, String healthUrl, String serviceUrl, Map<String, String> metadata) {
        this.pid = pid;
        this.host = host;
        this.name = name;
        this.managementUrl = managementUrl;
        this.healthUrl = healthUrl;
        this.serviceUrl = serviceUrl;
        this.metadata = metadata;
    }

    public Map<String, String> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

}
