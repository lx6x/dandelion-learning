package org.dandelion.starter.monitor.client.register;

/**
 * @author lx6x
 * @date 2023/11/29
 */
public class DefaultApplicationRegister implements ApplicationRegister {

    private final ApplicationFactory applicationFactory;

    public DefaultApplicationRegister(ApplicationFactory applicationFactory) {
        this.applicationFactory = applicationFactory;
    }

    @Override
    public boolean register() {

        Application application = this.applicationFactory.createApplication();
        System.out.println(application);
        return false;
    }

    @Override
    public void deregister() {

    }

    @Override
    public String getRegisteredId() {
        return null;
    }
}
