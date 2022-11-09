package org.dandelion.quartz.a.service;

import org.dandelion.quartz.a.entity.QuartzJob;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/9/14 13:45
 */
public interface QuartzService {

    /**
     * 新增job
     *
     * @param job .
     * @author L
     */
    void saveJob(QuartzJob job);

    /**
     * 触发job 手动执行
     *
     * @param jobName  名称
     * @param jobGroup 分组
     * @author L
     */
    void triggerJob(String jobName, String jobGroup);

    /**
     * 暂停job
     *
     * @param jobName  名称
     * @param jobGroup 分组
     * @author L
     */
    void pauseJob(String jobName, String jobGroup);


    /**
     * 恢复job
     *
     * @param jobName  名称
     * @param jobGroup 分组
     * @author L
     */
    void resumeJob(String jobName, String jobGroup);

    /**
     * 删除job
     *
     * @param jobName  名称
     * @param jobGroup 分组
     * @author L
     */
    void removeJob(String jobName, String jobGroup);

    /**
     * 检查任务是否存在
     *
     * @param jobName  名称
     * @param jobGroup 分组
     * @return boolean
     * @author L
     */
    boolean checkExists(String jobName, String jobGroup);
}
