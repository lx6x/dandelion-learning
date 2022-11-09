package org.dandelion.quartz.b.job;

import org.dandelion.commons.utils.DateUtils;
import org.quartz.*;
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
        Trigger trigger = jobExecutionContext.getTrigger();
        // 获取任务设定开始时间
        Date startTime = trigger.getStartTime();
        Date endTime = trigger.getEndTime();
        Date nowDate = DateUtils.getNowDate();
        assert nowDate != null;
        if(nowDate.getTime()>startTime.getTime() && nowDate.getTime()>endTime.getTime()){
            // 创建新任务后，如果当前开始时间小于当前时间
            return;
        }
        String nowDateString = DateUtils.getNowDate(nowDate);
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        Object param = mergedJobDataMap.get("param");
        // 任务的具体逻辑
        System.err.println("JobRelease  任务调度：" + nowDateString + " - - " + param);
    }
}
