package org.dandelion.netty.im.controller;

import org.dandelion.netty.common.bean.ChatInfo;
import org.dandelion.netty.common.bean.ServerInfo;
import org.dandelion.netty.common.bean.UserInfo;
import org.dandelion.netty.common.constant.BasicConstant;
import org.dandelion.netty.im.service.RouteService;
import org.dandelion.netty.common.utils.RedisUtils;
import org.dandelion.netty.common.utils.RoundRobin;
import org.dandelion.netty.common.utils.ZkClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO route controller
 *
 * @author L
 * @version 1.0
 * @date 2022/5/16 17:46
 */
@RestController
public class ImRoutingController {
    private static final Logger logger = LoggerFactory.getLogger(ImRoutingController.class);

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private RouteService routeService;


    @Resource(name = "zkClientUtil")
    private ZkClientUtil zkClientUtil;

    @PostMapping("/login")
    public ServerInfo login(@RequestBody UserInfo userInfo) {
        List<String> allNode = zkClientUtil.getAllNode();

        if (allNode.isEmpty()) {
            logger.info("----> no server start ...");
            return null;
        }

        RoundRobin roundRobin = new RoundRobin(allNode);
        String node = roundRobin.selectNode();

        // set the binding relationship between client and server
        redisUtils.set(BasicConstant.ROUTE_PREFIX + userInfo.getUserId(), node);
        logger.info("----> client banding server: {},{}", BasicConstant.ROUTE_PREFIX + userInfo.getUserId(), node);

        String[] server = node.split(":");
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setIp(server[0]);
        serverInfo.setNettyPort(Integer.valueOf(server[1]));
        serverInfo.setHttpPort(Integer.valueOf(server[2]));
        return serverInfo;
    }

    @PostMapping("/chat")
    public void chat(@RequestBody ChatInfo chatInfo) {
        logger.info("----> take over client message: {}", chatInfo.toString());
        String userId = chatInfo.getUserId();
        String key = (String) redisUtils.get(BasicConstant.ROUTE_PREFIX + userId);

        if (StringUtils.isEmpty(key)) {
            logger.warn("----> the current user [" + userId + "] is not logged in");
            return;
        }

        List<String> allNode = zkClientUtil.getAllNode();
        allNode.forEach(node -> {
            String[] server = node.split(":");
            String ip = server[0];
            Integer httpPort = Integer.valueOf(server[2]);
            String url = "http://" + ip + ":" + httpPort + "/pushMessage";
            routeService.sendMessage(url, chatInfo);
        });


    }
}
