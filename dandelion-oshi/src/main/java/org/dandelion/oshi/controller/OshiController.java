package org.dandelion.oshi.controller;

import cn.hutool.system.oshi.OshiUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.NetworkIF;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

@RestController
public class OshiController {

    /*@RequestMapping("/getIp")
    public String getIp() {

        StringBuffer sb = new StringBuffer();
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            sb.append(localHost).append("\r");

            //获取网卡，获取地址
            byte[] mac = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    //每2位用“-”拼接
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    //十位补0
                    sb.append("0").append(str);
                } else {
                    sb.append(str);
                }
            }
            System.out.println("本机MAC地址：" + sb.toString().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }*/

    @RequestMapping("/getIp")
    public String getIp() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        return localHost.getHostAddress();
    }

    @GetMapping("/getMac")
    public List<Map<String, String>> getMacAndIp() throws SocketException {
        List<Map<String, String>> listMap = new ArrayList<>();
        // 获取当前主机的所有网络接口，至少包含一个回环ip地址 127.0.0.1
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            // 当前节点
            NetworkInterface anInterface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = anInterface.getInetAddresses();
            // 该网卡接口下的ip会有多个，也需要一个个的遍历，找到自己所需要的
            while (addresses.hasMoreElements()) {
                InetAddress inetAddress = addresses.nextElement();
                Map<String, String> map = new HashMap<>(3);
                // 排除回环地址，不是回环的地址
                if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) { // 是否本地回环地址 是 返回 true
                    // 获取 MAC地址
                    NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
                    byte[] mac = network.getHardwareAddress();
                    StringBuilder macs = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        // 格式化十六进制
                        macs.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    map.put("mac", macs.toString());
                    map.put("ip", inetAddress.getHostAddress());
                    map.put("name", inetAddress.getHostName());
                    listMap.add(map);
                }
            }
        }
        return listMap;
    }

    @GetMapping("/test")
    public void test() {
        List<String> oshi = new ArrayList<>();
        List<NetworkIF> list = OshiUtil.getHardware().getNetworkIFs();
        StringBuilder sb = new StringBuilder("Network Interfaces:");
        if (list.isEmpty()) {
            sb.append(" Unknown");
        } else {
            for (NetworkIF net : list) {
                sb.append("\n ").append(net.toString());
            }
        }
        oshi.add(sb.toString());

        System.out.println(oshi);
    }
}

