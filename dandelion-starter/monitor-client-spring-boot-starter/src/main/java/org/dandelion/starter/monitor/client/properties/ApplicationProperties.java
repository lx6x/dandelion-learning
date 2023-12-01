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
    private String name;

    /**
     * 服务地址信息
     */
    private HostType hostType = HostType.IP;

    /**
     * 执行时间间隔ms 默认10s
     */
    private Long period = 10000L;

    /**
     * 设备主机唯一标识键
     */
    private String collectionId;

    private String serverUrl;



}
