package org.dandelion.netty.im.service;

import com.alibaba.fastjson.JSONObject;
import org.dandelion.netty.im.bean.ChatInfo;
import org.dandelion.netty.im.utils.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * TODO 简单点不做实现类
 *
 * @author L
 * @version 1.0
 * @date 2022/5/18 16:52
 */
@Service
public class RouteService {

    public void sendMessage(String url, ChatInfo chat){
        
        String string= JSONObject.toJSONString(chat);
        HttpUtils.post(url, string);
    }
}
