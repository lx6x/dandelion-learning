package org.dandelion.start.monitor.server.register;

import org.dandelion.start.monitor.server.register.model.ServiceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lx6x
 * @date 2023/12/4
 */
public class InstancesRegister {

    private static final ConcurrentHashMap<String,ServiceInfo> instanceService = new ConcurrentHashMap<>(16);

    public void save(ServiceInfo serviceInfo) {
        instanceService.put(serviceInfo.getName().concat(":").concat(serviceInfo.getCollectionId()),serviceInfo);
    }

    public static ConcurrentHashMap<String,ServiceInfo> getInstanceService() {
        return instanceService;
    }


}
