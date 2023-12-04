package org.dandelion.start.monitor.server.register;

import org.dandelion.start.monitor.server.register.model.ServiceInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx6x
 * @date 2023/12/1
 */
@RestController
public class RegisterController {

    @PostMapping(path = "/instances", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody ServiceInfo serviceInfo) {
        System.out.println(serviceInfo.toString());
    }
}
