package org.dandelion.starter.monitor.client.application;

import org.dandelion.starter.monitor.client.properties.ApplicationProperties;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lx6x
 * @date 2023/11/29
 */
public class DefaultServiceInfoFactory implements ServiceInfoFactory {

    private final ApplicationProperties applicationProperties;
    public DefaultServiceInfoFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties=applicationProperties;
    }

    @Override
    public ServiceInfo createApplication() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        // 获取进程 ID
        String processId = runtimeMXBean.getName().split("@")[0];
        return ServiceInfo.builder()
                .name(applicationProperties.getName())
                .pid(processId)
                .host(getHost(getLocalHost()))
                .collectionId(applicationProperties.getCollectionId())
                .build();
    }

    protected InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        }
        catch (UnknownHostException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    protected String getHost(InetAddress address) {
        return switch (this.applicationProperties.getHostType()) {
            case IP -> address.getHostAddress();
            case HOST_NAME -> address.getHostName();
            default -> address.getCanonicalHostName();
        };
    }
}
