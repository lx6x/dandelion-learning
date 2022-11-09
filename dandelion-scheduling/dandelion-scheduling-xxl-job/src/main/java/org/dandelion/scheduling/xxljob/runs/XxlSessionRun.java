package org.dandelion.scheduling.xxljob.runs;

import org.dandelion.scheduling.xxljob.service.CookiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * TODO 启动是获取 xxl-job 访问令牌
 *
 * @author L
 * @version 1.0
 * @date 2021/11/5 16:26
 */
@Component
public class XxlSessionRun {

    private final static Logger logger = LoggerFactory.getLogger(XxlSessionRun.class);

    @Autowired
    private CookiesService cookiesService;


    @Value("${xxl.job.local.url}")
    private String url;

    @PostConstruct
    public void xxlSession() {
        cookiesService.getCookies(url + "/login?userName=admin&password=123456");
        logger.info("======>> 存储 xxl-job cookie 信息");
    }


}
