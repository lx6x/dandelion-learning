package org.dandelion.scheduling.rocketmq.job;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liujunfei
 * @date 2023/5/16
 */
@Component
public class ProductJob {
    @Autowired
    public RocketMQTemplate rocketMQTemplate;
    @Scheduled(cron = "0 * * * * ? ")
    public void a(){
        rocketMQTemplate.convertAndSend("topic_a","aaa");
    }

    @Scheduled(cron = "10 * * * * ? ")
    public void b(){
        rocketMQTemplate.convertAndSend("topic_b","bbb");
    }
}
