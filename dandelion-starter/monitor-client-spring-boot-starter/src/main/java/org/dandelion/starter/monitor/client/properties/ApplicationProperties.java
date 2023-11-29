package org.dandelion.starter.monitor.client.properties;

import lombok.Data;
import org.dandelion.starter.monitor.client.enmu.HostType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lx6x
 * @date 2023/11/29
 */
@Data
@ConfigurationProperties(prefix = "mc")
public class ApplicationProperties {

    /**
     * 服务名称
     */
    @Value("${spring.application.name:spring-boot-application}")
    private String name = "spring-boot-application";

    /**
     * 服务地址信息
     */
    private HostType hostType = HostType.IP;



}
