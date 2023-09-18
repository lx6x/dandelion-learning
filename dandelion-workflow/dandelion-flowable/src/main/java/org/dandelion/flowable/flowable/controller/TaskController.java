package org.dandelion.flowable.flowable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.dandelion.flowable.common.R;
import org.dandelion.flowable.flowable.model.dto.FlowViewerDTO;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "任务相关")
@RestController
@RequestMapping("/dandelion/task")
public class TaskController {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    protected TaskService taskService;

    @Resource
    protected HistoryService historyService;

    @Operation(summary = "获取流程变量")
    @GetMapping(value = "/processVariables/{taskId}")
    public R<Map<String, Object>> processVariables(@Parameter(name = "taskId",description = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
        // 流程变量
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().includeProcessVariables().finished().taskId(taskId).singleResult();
        if (Objects.nonNull(historicTaskInstance)) {
            return R.ok(historicTaskInstance.getProcessVariables());
        } else {
            Map<String, Object> variables = taskService.getVariables(taskId);
            return R.ok(variables);
        }
    }

    /**
     * 流程节点信息
     *
     * @param procInsId 模型id
     * @param deployId  部署id
     * @return .
     */
    @Operation(summary = "流程节点信息")
    @GetMapping("/flowXmlAndNode")
    public R<Map<String, Object>> flowXmlAndNode(@RequestParam(value = "procInsId", required = false) String procInsId,
                                                 @RequestParam(value = "deployId", required = false) String deployId) {
        try {
            List<FlowViewerDTO> flowViewerList = new ArrayList<>();
            // 获取已经完成的节点
            List<HistoricActivityInstance> listFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(procInsId)
                    .finished()
                    .list();

            // 保存已经完成的流程节点编号
            listFinished.forEach(s -> {
                FlowViewerDTO flowViewerDto = new FlowViewerDTO();
                flowViewerDto.setKey(s.getActivityId());
                flowViewerDto.setCompleted(true);
                flowViewerList.add(flowViewerDto);
            });

            // 获取代办节点
            List<HistoricActivityInstance> listUnFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(procInsId)
                    .unfinished()
                    .list();

            // 保存需要代办的节点编号
            listUnFinished.forEach(s -> {
                FlowViewerDTO flowViewerDto = new FlowViewerDTO();
                flowViewerDto.setKey(s.getActivityId());
                flowViewerDto.setCompleted(false);
                flowViewerList.add(flowViewerDto);
            });
            Map<String, Object> result = new HashMap<>(16);
            // xmlData 数据
            ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
            InputStream inputStream = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
            String xmlData = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            result.put("nodeData", flowViewerList);
            result.put("xmlData", xmlData);
            return R.ok(result);
        } catch (Exception e) {
            return R.fail("高亮历史任务失败");
        }
    }


}
