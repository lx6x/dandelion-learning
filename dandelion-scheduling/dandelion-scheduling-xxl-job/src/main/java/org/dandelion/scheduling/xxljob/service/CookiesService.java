package org.dandelion.scheduling.xxljob.service;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dandelion.commons.model.constants.RedisConstant;
import org.dandelion.commons.utils.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * TODO xxl-job cookie 存储
 *
 * @author L
 * @version 1.0
 * @date 2021/11/8 11:32
 */
@Service
public class CookiesService {

    private final static Logger logger = LoggerFactory.getLogger(CookiesService.class);

    @Autowired
    private RedisUtils redisUtils;

    @Value("${xxl.job.login.cookie.expired.time}")
    private Integer cookieExpiredTime;

    /**
     * 获取 xxl-job cookie 信息，并保存在 redis 中
     *
     * @param url xxl-job路径
     * @author L
     */
    public void getCookies(String url) {
        // 创建httpClient
        CookieStore httpCookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
        // 创建post请求方式实例
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头 发送的是json数据格式
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        // 设置参数---设置消息实体 也就是携带的数据
        StringEntity entity = new StringEntity("{}", StandardCharsets.UTF_8);
        // 设置编码格式
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        // 把请求消息实体塞进去
        httpPost.setEntity(entity);

        try {
            // 执行http的post请求
            httpClient.execute(httpPost);
            List<Cookie> cookies = httpCookieStore.getCookies();
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                boolean iswExists = redisUtils.hasKey(name);
                if (!iswExists) {
                    String xxlJobLoginIdentity = RedisConstant.XxlJob.XXL_JOB_LOGIN_IDENTITY;
                    // 设置过期
                    redisUtils.set(xxlJobLoginIdentity, value, cookieExpiredTime);
                    logger.info("xxl-job cookie key：{} - value：{} - expired time：{}", xxlJobLoginIdentity, value, cookieExpiredTime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
