package org.dandelion.scheduling.xxljob.listeners;


import org.dandelion.commons.model.constants.RedisConstant;
import org.dandelion.scheduling.xxljob.service.CookiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * TODO  监听订阅
 *
 * @author L
 * @version 1.0
 * @date 2021/11/8 10:45
 */
@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private final static Logger logger = LoggerFactory.getLogger(RedisKeyExpiredListener.class);

    @Autowired
    private CookiesService cookiesService;

    @Value("${xxl.job.local.url}")
    private String url;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.·
     */
    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获取过期的key
        String expiredKey = message.toString();
        // 判断是否是想要监听的过期key
        if (expiredKey.contains(RedisConstant.XxlJob.XXL_JOB_LOGIN_IDENTITY)) {
            logger.info("======>> redis key过期：{}", expiredKey);
            cookiesService.getCookies(url + "/login?userName=admin&password=123456");
            logger.info("======>> 重新存储 xxl-job cookie 信息");
        }
    }
}
