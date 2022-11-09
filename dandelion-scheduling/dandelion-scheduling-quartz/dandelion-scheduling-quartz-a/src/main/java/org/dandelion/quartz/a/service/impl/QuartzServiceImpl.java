package org.dandelion.quartz.a.service.impl;

import org.dandelion.quartz.a.components.JobFactory;
import org.dandelion.quartz.a.entity.QuartzJob;
import org.dandelion.quartz.a.service.QuartzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO Quartz 三个核心概念 调度器-Scheduler 任务-JobDetail 触发器-Trigger
 *
 * @author L
 * @version 1.0
 * @date 2021/9/14 13:45
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    private final Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);

    /**
     * 调度器，Quartz通过调度器来注册，暂停，删除 Trigger 和 JobDetail
     */
    @Autowired
    private Scheduler scheduler;

    @Override
    public void saveJob(QuartzJob job) {
        String jobName = job.getJobName();
        String jobJobGroup = job.getJobGroup();
        if (checkExists(jobName, jobJobGroup)) {
            return;
        }

        JobDetail jobDetail = JobBuilder
                // 调用具体方法实现
                .newJob(JobFactory.getJob(job.getType()))
                // 注:JobDetail和Trigger需要设置组名和自己的名字，用来作为唯一标识。
                // ，JobDetail和Trigger的唯一标识可以相同，因为他们是不同的类。
                .withIdentity(jobName, jobJobGroup)
                .storeDurably()
                .build();


        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("param", job.getParams());

        Trigger cronTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobJobGroup, jobName)
                // 执行开始时间
                .startAt(getDate(job.getStartingTime()))
                // 执行结束时间
                .endAt(getDate(job.getEndTime()))
                // Trigger通过cron表达式指定了任务执行的周期
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .withDescription("定时说明")
                .build();

        try {
            scheduler.start();
            //交由Scheduler安排触发
            Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
            logger.info("任务保存：" + getNowDate(date));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void triggerJob(String jobName, String jobGroup) {
        //
        JobKey job = new JobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeJob(String jobName, String jobGroup) {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeJob(String jobName, String jobGroup) {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkExists(String jobName, String jobGroup) {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNowDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public Date getDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
