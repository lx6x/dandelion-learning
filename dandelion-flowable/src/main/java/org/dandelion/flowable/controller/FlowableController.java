package org.dandelion.flowable.controller;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liujunfei
 * @date 2023/6/13
 */
@RestController
public class FlowableController {


    @Resource
    private RuntimeService runtimeService;

    @GetMapping("get")
    public String get() {
        return "flowable";
    }

    @GetMapping("/startProcess")
    public void startProcess() {
        // 启动流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test");

        // 获取流程实例ID
        String processInstanceId = processInstance.getId();

        // 获取流程实例的变量
        Map<String, Object> variables = runtimeService.getVariables(processInstanceId);

        // 设置流程实例的变量
        runtimeService.setVariable(processInstanceId, "variableName", "status");

        // 其他处理
    }
}

