package org.dandelion.scheduling.spring.example;

import org.dandelion.commons.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lx6x
 * @date 2023/8/31
 */
@Component
public class ScheduledExample {

    @Scheduled(cron = "0 * * * * ? ")
    public void scheduled() {
        System.out.println(DateUtils.getNowDateTime(new Date()));
    }

}
