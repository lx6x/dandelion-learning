package com.dandelion.actuator.register;

/**
 * @author lx6x
 * @date 2023/11/29
 */
public interface ApplicationFactory {

    /**
     * @return {@link Application} instance;
     */
    Application createApplication();
}
