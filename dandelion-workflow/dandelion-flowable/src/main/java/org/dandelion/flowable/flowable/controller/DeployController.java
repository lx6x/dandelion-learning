package org.dandelion.flowable.flowable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.flowable.common.R;
import org.dandelion.flowable.flowable.model.vo.DeployVo;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "流程部署")
@RestController
@RequestMapping("/workflow/deploy")
public class DeployController {

    private static final Logger logger = LoggerFactory.getLogger(DeployController.class);

    @Resource
    private ModelService modelService;
    @Resource
    private RepositoryService repositoryService;

    /**
     * 查询流程部署列表
     */
    @Operation(summary = "查询流程部署列表")
    @GetMapping("/list")
    public R<List<DeployVo>> list() {
        // 流程定义列表数据查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .orderByProcessDefinitionKey()
                .asc();
        // TODO 可以条件搜索
        List<ProcessDefinition> definitionList = processDefinitionQuery.list();
        List<DeployVo> deployVoList = new ArrayList<>(definitionList.size());
        for (ProcessDefinition processDefinition : definitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            DeployVo vo = new DeployVo();
            vo.setDefinitionId(processDefinition.getId());
            vo.setProcessKey(processDefinition.getKey());
            vo.setProcessName(processDefinition.getName());
            vo.setVersion(processDefinition.getVersion());
            vo.setCategory(processDefinition.getCategory());
            vo.setDeploymentId(processDefinition.getDeploymentId());
            vo.setSuspended(processDefinition.isSuspended());
            // 流程部署信息
            vo.setCategory(deployment.getCategory());
            vo.setDeploymentTime(deployment.getDeploymentTime());
            deployVoList.add(vo);
        }

        return R.ok(deployVoList);
    }

    /**
     * 查询流程部署版本列表
     *
     * @param processKey 流程定义主键
     */
    @Operation(summary = "查询流程部署版本列表")
    @GetMapping("/publishList")
    public R<List<DeployVo>> publishList(@Parameter(name = "processKey", description = "流程定义主键") @RequestParam String processKey) {
        // 创建查询条件
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .orderByProcessDefinitionVersion()
                .desc();

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                .list();
        List<DeployVo> deployVoList = processDefinitionList.stream().map(item -> {
            DeployVo vo = new DeployVo();
            vo.setDefinitionId(item.getId());
            vo.setProcessKey(item.getKey());
            vo.setProcessName(item.getName());
            vo.setVersion(item.getVersion());
            vo.setCategory(item.getCategory());
            vo.setDeploymentId(item.getDeploymentId());
            vo.setSuspended(item.isSuspended());
            return vo;
        }).collect(Collectors.toList());
        return R.ok(deployVoList);
    }


    /**
     * 删除流程模型
     *
     * @param deployIds 流程部署ids
     */
    @Operation(summary = "删除流程模型")
    @DeleteMapping("/{deployIds}")
    public R<String> remove(@NotEmpty(message = "主键不能为空") @PathVariable String[] deployIds) {
        for (String deployId : deployIds) {
            repositoryService.deleteDeployment(deployId, true);
        }
        return R.ok();
    }


}
