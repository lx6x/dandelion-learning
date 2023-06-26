package org.dandelion.flowable.flowable.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.flowable.engine.RepositoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "任务相关")
@RestController
@RequestMapping("/dandelion/task")
public class TaskController {

    /*@Resource
    private RuntimeService runtimeService;





    @GetMapping("/startProcessInstanceByKey/{key}")
    public String startProcessInstanceByKey(@PathVariable("key") String key) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        return processInstance.getId();
    }*/

    @Resource
    private RepositoryService repositoryService;


}
