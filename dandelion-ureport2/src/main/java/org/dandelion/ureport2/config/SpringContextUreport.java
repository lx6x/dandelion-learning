package org.dandelion.ureport2.config;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.Servlet;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/07/01 16:31
 */
@Configuration
@ImportResource("classpath:ureport-console-context.xml") // 加载ureport对应的xml配置文件
public class SpringContextUreport {
    @Bean
    public ServletRegistrationBean<Servlet> ureport2Servlet(){
        return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
    }
}
