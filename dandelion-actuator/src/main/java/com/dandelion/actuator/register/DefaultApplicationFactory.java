package com.dandelion.actuator.register;

import org.springframework.boot.web.context.WebServerInitializedEvent;

/**
 * @author lx6x
 * @date 2023/11/29
 */
public class DefaultApplicationFactory implements ApplicationFactory {

    private final WebServerInitializedEvent event;

    public DefaultApplicationFactory(WebServerInitializedEvent event) {
        this.event = event;
    }

    @Override
    public Application createApplication() {
        return Application.builder().build();
    }
}
