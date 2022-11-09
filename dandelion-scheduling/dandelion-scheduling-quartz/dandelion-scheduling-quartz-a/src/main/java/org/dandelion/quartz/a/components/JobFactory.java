package org.dandelion.quartz.a.components;

import org.dandelion.quartz.a.constants.SchedulerConstant;
import org.dandelion.quartz.a.job.JobRelease;
import org.dandelion.quartz.a.job.JobStartQuartz;
import org.quartz.Job;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * TODO 参考：https://www.cnblogs.com/daxin/p/3608320.html
 *
 * @author L
 * @version 1.0
 * @date 2021/9/15 11:26
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;


    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

    /**
     * 根据传入值判断调用哪个 job
     *
     * @param type 类型
     * @return 对应执行类
     * @author L
     */
    public static Class<? extends Job> getJob(String type) {
        switch (type) {
            case SchedulerConstant.JOB.RELEASE:
                return JobRelease.class;
            case SchedulerConstant.JOB.START_QUARTZ:
                return JobStartQuartz.class;
            default:
                return null;
        }

    }
}
