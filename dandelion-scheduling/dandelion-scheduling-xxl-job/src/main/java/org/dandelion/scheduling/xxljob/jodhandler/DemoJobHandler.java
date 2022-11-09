package org.dandelion.scheduling.xxljob.jodhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TODO Bean模式任务（类形式）
 *      优点:
 *          不限制项目环境，兼容性好。即使是无框架项目，如main方法直接启动的项目也可以提供支持，
 *          可以参考示例项目 “xxl-job-executor-sample-frameless”；
 *      缺点：
 *          每个任务需要占用一个Java类，造成类的浪费；
 *          不支持自动扫描任务并注入到执行器容器，需要手动注入。
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 10:59
 */
@Component
public class DemoJobHandler extends IJobHandler {

    private static Logger logger = LoggerFactory.getLogger(DemoJobHandler.class);

    @XxlJob("demoJobHandler")
    @Override
    public void execute() throws Exception {
        String jobParam = XxlJobHelper.getJobParam();
        System.err.println("jobParam：" + jobParam);
        XxlJobHelper.handleSuccess();
    }

}
