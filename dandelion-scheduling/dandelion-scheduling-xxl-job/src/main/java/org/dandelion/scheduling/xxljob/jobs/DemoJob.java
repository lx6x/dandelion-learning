package org.dandelion.scheduling.xxljob.jobs;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TODO Bean模式任务（方法形式）
 *      优点：
 *          每个任务只需要开发一个方法，并添加”@XxlJob”注解即可，更加方便、快速。
 *          支持自动扫描任务并注入到执行器容器。
 *      缺点：
 *          要求Spring容器环境；
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 11:08
 */
@Component
public class DemoJob {

    private static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    @XxlJob("demoJob")
    public ReturnT<String> domoJod() {
        String jobParam = XxlJobHelper.getJobParam();
        System.err.println("jobParam: " + jobParam);
        return ReturnT.SUCCESS;
    }
}
