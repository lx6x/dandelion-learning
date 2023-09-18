package org.dandelion.flowable.flowable.controller;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.flowable.common.R;
import org.dandelion.flowable.flowable.converter.ModelConverter;
import org.dandelion.flowable.flowable.model.dto.DeployModelDTO;
import org.dandelion.flowable.flowable.model.entity.ActDeModelDO;
import org.dandelion.flowable.flowable.service.IActDeModelService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.rest.app.ModelBpmnResource;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liujunfei
 * @date 2023/8/25
 */
@Tag(name = "流程模型管理")
@RestController
@RequestMapping("/workflow/model")
public class ModelController {

    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

    @Resource
    private ModelBpmnResource modelBpmnResource;
    @Resource
    private IActDeModelService iActDeModelService;
    @Resource
    private ModelConverter modelConverter;
    @Resource
    private ModelService modelService;
    @Resource
    private RepositoryService repositoryService;

    /**
     * 流程模型列表
     *
     * @return list
     */
    @Operation(summary = "流程模型列表")
    @GetMapping("/list")
    public R<List<ActDeModelDO>> modelList() {
        // ui中调用接口
        // modelQueryService.getModels(null, sort, modelType, request)
        return R.ok(iActDeModelService.list());
    }


    /**
     * 获取流程模型详细信息
     *
     * @param modelId 模型主键
     */
    @Operation(summary = "流程模型详细信息")
    @GetMapping(value = "/{modelId}")
    public R<ActDeModelDO> getInfo(@Parameter(name = "modelId", description = "模型主键") @NotNull(message = "主键不能为空") @PathVariable("modelId") String modelId) {
        // 获取流程模型
        // return R.ok(modelService.getModel(modelId));
        return R.ok(iActDeModelService.getById(modelId));
    }

    /**
     * 获取流程模型详细信息
     *
     * @param modelId 模型主键
     */
    /*@Operation(summary = "流程模型详细信息 repository.Model")
    @GetMapping(value = "/getInfoRepository/{modelId}")
    public R<org.flowable.engine.repository.Model> getInfoRepository(@Parameter(name = "modelId", description = "模型主键") @NotNull(message = "主键不能为空") @PathVariable("modelId") String modelId) {
        // 获取流程模型
        org.flowable.engine.repository.Model model = repositoryService.getModel(modelId);
        return R.ok(model);
    }*/

    /**
     * 流程模型部署
     *
     * @param deployModelDTO 流程部署DTO
     * @return 成功/失败
     */
    @Operation(summary = "流程模型部署")
    @PostMapping("/deploy")
    public R<String> deploy(@RequestBody DeployModelDTO deployModelDTO) {
        Model model = modelService.getModel(deployModelDTO.getId());
        if (ObjectUtil.isNull(model)) {
            return R.fail("流程模型不存在");
        }
        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        try {
            Deployment deploy = repositoryService.createDeployment()
                    .name(model.getName())
                    .key(model.getKey())
                    .category(deployModelDTO.getCategory())
                    .addBpmnModel(model.getKey() + ".bpmn", bpmnModel)
                    .deploy();

            ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deploy.getId())
                    .singleResult();

            // 修改流程定义的分类，便于搜索流程
            repositoryService.setProcessDefinitionCategory(procDef.getId(), deployModelDTO.getCategory());
            return R.ok("模型部署成功, Dep ID:" + deploy.getId());
        } catch (Exception var10) {
            logger.error("Model Deploy Error: ", var10);
            return R.fail("部署操作失败");
        }
    }

    /**
     * 导出model的xml文件
     */
    @Operation(summary = "导出流程模型xml文件")
    @GetMapping("exportBpmnXml")
    @Parameters({
            @Parameter(name = "id", description = "流程模型id", in = ParameterIn.PATH)
    })
    public void exportBpmnXml(String id, HttpServletResponse response) throws Exception {
        modelBpmnResource.getProcessModelBpmn20Xml(response, id);
    }
}
