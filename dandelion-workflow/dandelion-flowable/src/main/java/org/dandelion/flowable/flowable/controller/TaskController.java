package org.dandelion.flowable.flowable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.flowable.common.R;
import org.dandelion.flowable.common.generator.flow.CustomProcessDiagramGenerator;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "任务")
@RestController
@RequestMapping("/dandelion/task")
public class TaskController {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;
    @Resource
    protected RuntimeService runtimeService;
    @Qualifier("processEngine")
    @Resource
    protected ProcessEngine processEngine;

    @Operation(summary = "获取流程变量")
    @GetMapping(value = "/processVariables/{taskId}")
    public R<Map<String, Object>> processVariables(@Parameter(name = "taskId", description = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
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
     *//*
    @Operation(summary = "流程节点信息")
    @GetMapping("/flowXmlAndNode")
    @Parameters({
            @Parameter(name = "procInsId",description = "模型id"),
            @Parameter(name = "deployId",description = "部署id")
    })
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
    }*/

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @Operation(summary = "生成流程图")
    @GetMapping("/diagram/{processId}")
    @Parameters({
            @Parameter(name = "processId", description = "任务ID")
    })
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) {
        String processDefinitionId;
        // 获取当前的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        // 如果流程已经结束，则得到结束节点
        if (Objects.isNull(processInstance)) {
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();

            processDefinitionId = pi.getProcessDefinitionId();
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }

        // 获得活动的节点
        List<HistoricActivityInstance> highLightedFlowList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();

        List<String> highLightedFlows = new ArrayList<>();
        List<String> highLightedNodes = new ArrayList<>();
        //高亮线
        for (HistoricActivityInstance tempActivity : highLightedFlowList) {
            if ("sequenceFlow".equals(tempActivity.getActivityType())) {
                //高亮线
                highLightedFlows.add(tempActivity.getActivityId());
            } else {
                //高亮节点
                highLightedNodes.add(tempActivity.getActivityId());
            }
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
        //获取自定义图片生成器
        ProcessDiagramGenerator diagramGenerator = new CustomProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedNodes, highLightedFlows, configuration.getActivityFontName(),
                configuration.getLabelFontName(), configuration.getAnnotationFontName(), configuration.getClassLoader(), 1.0, true);

        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
