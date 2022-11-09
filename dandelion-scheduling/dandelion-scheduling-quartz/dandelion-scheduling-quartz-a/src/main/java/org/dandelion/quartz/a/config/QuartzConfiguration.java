package org.dandelion.quartz.a.config;


import org.dandelion.quartz.a.components.JobFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * TODO Quartz 配置信息
 *
 * @author L
 * @version 1.0
 * @date 2021/9/15 11:08
 */
@Configuration
public class QuartzConfiguration {

    @Autowired
    private JobFactory jobFactory;

    /**
     * 覆盖 Quartz 原生配置信息
     *
     * @return SchedulerFactoryBean 作用参考：https://blog.csdn.net/zhangxtn/article/details/49584071
     * @author L
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //延长启动
        schedulerFactoryBean.setStartupDelay(1);
        //设置加载的配置文件
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
