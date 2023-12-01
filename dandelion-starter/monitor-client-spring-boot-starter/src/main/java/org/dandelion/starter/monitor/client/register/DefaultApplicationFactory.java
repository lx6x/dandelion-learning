package org.dandelion.starter.monitor.client.register;

import org.dandelion.starter.monitor.client.properties.ApplicationProperties;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lx6x
 * @date 2023/11/29
 */
public class DefaultApplicationFactory implements ApplicationFactory {

    private final ApplicationProperties applicationProperties;
    public DefaultApplicationFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties=applicationProperties;
    }

    @Override
    public Application createApplication() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        // 获取进程 ID
        String processId = runtimeMXBean.getName().split("@")[0];
        return Application.builder().name(applicationProperties.getName()).pid(processId).host(getHost(getLocalHost())).build();
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
