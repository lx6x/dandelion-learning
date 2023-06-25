package org.dandelion.quartz.b.job;

import org.dandelion.commons.utils.DateUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * TODO Job接口的实现类，定义任务的具体内容 测试使用
 *
 * @author L
 * @version 1.0
 * @date 2021/9/14 13:37
 */
@Component
public class JobStartQuartz extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String nowDate = DateUtils.getNowDateTime(DateUtils.getNowDateTime());
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        Object param = mergedJobDataMap.get("param");
        // 任务的具体逻辑
        System.err.println("JobStartQuartz  任务调度：" + nowDate + " - - " + String.valueOf(param));
    }

}
