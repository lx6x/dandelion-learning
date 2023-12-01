package org.dandelion.starter.monitor.client.register;

import org.dandelion.starter.monitor.client.application.ServiceInfo;
import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * @author lx6x
 * @date 2023/11/30
 */
public class DefaultRegisterFactory implements RegisterFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRegisterFactory.class);

    private final ServiceInfo serviceInfo;

    private final RestTemplate restTemplate;

    private final ApplicationProperties applicationProperties;
    private static final ParameterizedTypeReference<Map<String, Object>> RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
    };


    public DefaultRegisterFactory(ServiceInfo serviceInfo, ApplicationProperties applicationProperties) {
        this.serviceInfo = serviceInfo;
        this.applicationProperties = applicationProperties;
        this.restTemplate = new RestTemplateBuilder().build();
    }


    @Override
    public void register() {
        System.out.println(serviceInfo.toString());

        String serverUrl = applicationProperties.getServerUrl();
        if (serverUrl != null) {

            ResponseEntity<Map<String, Object>> exchange = restTemplate.exchange(applicationProperties.getServerUrl(), HttpMethod.POST, new HttpEntity<>(serviceInfo), RESPONSE_TYPE);
            System.out.println(exchange);
        }
    }

    protected HttpHeaders createRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return HttpHeaders.readOnlyHttpHeaders(headers);
    }

}
