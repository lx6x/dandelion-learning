package org.dandelion.quartz.a.job;

import org.dandelion.commons.utils.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * TODO 发布 job
 *
 * @author L
 * @version 1.0
 * @date 2021/9/16 13:05
 */
@DisallowConcurrentExecution
public class JobRelease extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date fireTime = jobExecutionContext.getFireTime();
        System.err.println("-------> " + fireTime);
        String nowDate = DateUtils.getNowDateTime(DateUtils.getNowDateTime());
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        Object param = mergedJobDataMap.get("param");
        // 任务的具体逻辑
        System.err.println("JobRelease  任务调度：" + nowDate + " - - " + String.valueOf(param));
    }
}
