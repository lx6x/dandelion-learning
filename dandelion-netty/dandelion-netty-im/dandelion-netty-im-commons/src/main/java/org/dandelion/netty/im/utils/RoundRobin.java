package org.dandelion.netty.im.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/5/17 10:53
 */
public class RoundRobin {

    /**
     * 普通轮询算法
     */
    private static Integer index = 0;
    private final List<String> nodes;

    public RoundRobin(List<String> nodes) {
        this.nodes = nodes;
    }

    public String selectNode() {
        String node = null;
        synchronized (index) {
            // 下标复位
            if (index >= nodes.size()) {
                index = 0;
            }
            node = nodes.get(index);
            index++;
        }
        return node;
    }

    // 并发测试：两个线程循环获取节点
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();

        strings.add("192.168.1.101");
        strings.add("192.168.1.103");
        strings.add("192.168.1.102");

        /*new Thread(() -> {
            RoundRobin roundRobin1 = new RoundRobin(strings);
            for (int i = 1; i <= 5; i++) {
                String serverIp = roundRobin1.selectNode();
                System.out.println(Thread.currentThread().getName() + "==第" + i + "次获取节点：" + serverIp);
            }
        }).start();
        RoundRobin roundRobin2 = new RoundRobin(strings);
        for (int i = 1; i <= roundRobin2.nodes.size(); i++) {
            String serverIp = roundRobin2.selectNode();
            System.out.println(Thread.currentThread().getName() + "==第" + i + "次获取节点：" + serverIp);
        }*/


    }
}
