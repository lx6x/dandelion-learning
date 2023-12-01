package org.dandelion.starter.monitor.client.push;

import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.dandelion.starter.monitor.client.register.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * @author liujunfei
 * @date 2023/11/30
 */
public class DefaultPushFactory implements PushFactory {

    private static final Logger logger= LoggerFactory.getLogger(DefaultPushFactory.class);

    private final Application application;

    private final RestTemplate restTemplate;

    private final ApplicationProperties applicationProperties;
    private static final ParameterizedTypeReference<Map<String, Object>> RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
    };


    public DefaultPushFactory(Application application, ApplicationProperties applicationProperties) {
        this.application = application;
        this.applicationProperties = applicationProperties;
        this.restTemplate = new RestTemplateBuilder().build();
    }


    @Override
    public void register() {
        System.out.println(application.toString());
        // 直接动态写库
        // 推送mq
        // 向server注册，然后server通过actuator获取对应client服务信息

        ResponseEntity<Map<String, Object>> response = this.restTemplate.exchange(applicationProperties.getServerUrl().concat("/"), HttpMethod.POST,
                new HttpEntity<>(application, this.createRequestHeaders()), RESPONSE_TYPE);
        String id=response.getBody().get("id").toString();
        logger.info("register id: {}",id);
    }

    protected HttpHeaders createRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return HttpHeaders.readOnlyHttpHeaders(headers);
    }

}
