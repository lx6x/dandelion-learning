package org.dandelion.scheduling.spring.example;

import org.dandelion.commons.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
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

    public static void main(String[] args) {
        String expression = "0 0 3 * * ?";
        Date date = new Date();
        for (int i = 0; i < 100; i++) {
            date = getNextRunDate(expression, date);
            System.out.println(date);
        }
    }

    public static Date getNextRunDate(String expression, Date date) {
        CronTrigger cron = new CronTrigger(expression);
        return cron.nextExecutionTime(new SimpleTriggerContext(date, date, date));
    }


}
