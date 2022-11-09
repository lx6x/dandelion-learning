package org.dandelion.scheduling.xxljob.feign;

import org.dandelion.scheduling.xxljob.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO xxl-job
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 17:55
 */
@FeignClient(contextId = "XxlJobAdminFeign", url = "${xxl.job.local.url}", name = "XxlJobAdminFeign", configuration = FeignConfiguration.class)
public interface XxlJobAdminFeign {

    /**
     * 首页列表
     *
     * @param start           .
     * @param author          .
     * @param executorHandler .
     * @param jobDesc         .
     * @param jobGroup        .
     * @param length          .
     * @param triggerStatus   .
     * @return map
     * @author L
     */
    @RequestMapping(value = "/jobinfo/pageList", method = RequestMethod.POST)
    String pageList(@RequestParam("start") int start,
                    @RequestParam("length") int length,
                    @RequestParam("jobGroup") int jobGroup,
                    @RequestParam("triggerStatus") int triggerStatus,
                    @RequestParam("jobDesc") String jobDesc,
                    @RequestParam("executorHandler") String executorHandler,
                    @RequestParam("author") String author);

    /**
     * 任务新增
     *
     * @param jobGroup=3& jobDesc=%E6%B5%8B%E8%AF%95&
     *                    author=%E5%BC%A0%E4%B8%89&
     *                    alarmEmail=&
     *                    scheduleType=CRON&
     *                    scheduleConf=0%2F5+*+*+*+*+%3F&
     *                    cronGen_display=0%2F5+*+*+*+*+%3F&
     *                    schedule_conf_CRON=&
     *                    schedule_conf_FIX_RATE=&
     *                    schedule_conf_FIX_DELAY=&
     *                    glueType=BEAN&
     *                    executorHandler=demoJob&
     *                    executorParam=&
     *                    executorRouteStrategy=FIRST&
     *                    childJobId=&
     *                    misfireStrategy=DO_NOTHING&
     *                    executorBlockStrategy=SERIAL_EXECUTION&
     *                    executorTimeout=0&
     *                    executorFailRetryCount=0&
     *                    glueRemark=GLUE%E4%BB%A3%E7%A0%81%E5%88%9D%E5%A7%8B%E5%8C%96&
     *                    glueSource=
     *                    参数太多 不想测试
     * @return string
     * @author L
     */
    @RequestMapping("")
    String add();
}
