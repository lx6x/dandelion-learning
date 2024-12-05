package org.dandelion.commons.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ping
 *
 * @author lx6x
 */
public class PingStatus {

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("192.168.1.1");
            boolean reachable = address.isReachable(2000); // 尝试在5000毫秒内检查是否可达
            if (reachable) {
                System.out.println("IP is reachable.");
            } else {
                System.out.println("IP is not reachable.");
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }
}
