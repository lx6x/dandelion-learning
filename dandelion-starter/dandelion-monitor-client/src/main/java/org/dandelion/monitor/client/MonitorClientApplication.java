package org.dandelion.monitor.client;

import org.dandelion.starter.monitor.client.EnableMonitorClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lx6x
 * @date 2023/12/1
 */
@EnableMonitorClient
@SpringBootApplication
public class MonitorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorClientApplication.class, args);
    }
}
