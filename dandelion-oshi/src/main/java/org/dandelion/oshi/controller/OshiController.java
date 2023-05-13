package org.dandelion.oshi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.NetworkInterface;

@RestController
public class OshiController {

    @RequestMapping("/getIp")
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
    }
}

