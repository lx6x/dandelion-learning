package org.dandelion.flowable.flowable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.flowable.common.R;
import org.dandelion.flowable.flowable.converter.ActDeModelConverter;
import org.dandelion.flowable.flowable.model.dto.DeployModelDTO;
import org.dandelion.flowable.flowable.model.vo.ActDeModelVO;
import org.dandelion.flowable.flowable.service.IActDeModelService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.domain.ModelRelation;
import org.flowable.ui.modeler.rest.app.ModelBpmnResource;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.ui.task.service.api.DeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "流程模型")
@RestController
@RequestMapping("/dandelion/model")
public class ModelController {

    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

    @Resource
    private ModelBpmnResource modelBpmnResource;
    @Resource
    protected FlowableModelQueryService modelQueryService;
    @Resource
    private IActDeModelService iActDeModelService;
    @Resource
    private ActDeModelConverter actDeModelConverter;
    @Resource
    private ModelService modelService;
    @Resource
    private DeploymentService clientService;
    @Resource
    private RepositoryService repositoryService;

    /**
     * 导出model的xml文件
     */
    @Operation(summary = "导出model的xml文件")
    @GetMapping("exportBpmnXml")
    @Parameters({
            @Parameter(name = "id", description = "流程模型id", in = ParameterIn.PATH)
    })
    public void exportBpmnXml(String id, HttpServletResponse response) throws Exception {
        modelBpmnResource.getProcessModelBpmn20Xml(response, id);
    }

    /**
     * 流程模型列表
     *
     * @return list
     */
    @Operation(summary = "流程模型列表")
    @GetMapping("/list")
    public R<List<ActDeModelVO>> modelList() {
        // ui中调用接口
        // modelQueryService.getModels(null, sort, modelType, request)
        return R.success(actDeModelConverter.do2vo(iActDeModelService.list()));
    }

    /*@GetMapping(produces = "application/json")
    public JsonNode listDeployments(HttpServletRequest request) {
        logger.debug("REST request to get a list of deployments");

        JsonNode resultNode = null;
        ServerConfig serverConfig = retrieveServerConfig(EndpointType.PROCESS);
        Map<String, String[]> parameterMap = getRequestParametersWithoutServerId(request);

        try {
            resultNode = clientService.listDeployments(serverConfig, parameterMap);

        } catch (FlowableServiceException e) {
            LOGGER.error("Error getting deployments", e);
            throw new BadRequestException(e.getMessage());
        }

        return resultNode;
    }*/


    /**
     * 流程部署
     *
     * @param deployModelDTO 流程部署DTO
     * @return 成功/失败
     */
    @Operation(summary = "流程部署")
    @PostMapping("/deploy")
    public R<String> deploy(@RequestBody DeployModelDTO deployModelDTO) {
        Model model = modelService.getModel(deployModelDTO.getId());
        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        try {
            Deployment deploy = repositoryService.createDeployment()
                    .name(model.getName())
                    .key(model.getKey())
                    .category(deployModelDTO.getCategory())
                    .addBpmnModel(model.getKey() + ".bpmn", bpmnModel)
                    .deploy();
           /* List<ModelRelation> modelRelations = this.modelRelationRepository.findByParentModelIdAndType(model.getId(), "form-model");
            Iterator var7 = modelRelations.iterator();

            while (var7.hasNext()) {
                ModelRelation modelRelation = (ModelRelation) var7.next();
                Model form = this.modelService.getModel(modelRelation.getModelId());
                this.formRepositoryService.createDeployment().parentDeploymentId(deploy.getId()).name(form.getName()).addFormDefinition(form.getName() + ".form", form.getModelEditorJson()).deploy();
            }*/

            return R.success("模型部署成功, Dep ID:" + deploy.getId());
        } catch (Exception var10) {
            logger.error("Model Deploy Error: ", var10);
            return R.fail("部署操作失败");
        }
    }

    @Operation(summary = "流程已部署列表")
    @GetMapping("/deployList")
    public R<List<ProcessDefinition>> deployList(){
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();

        return R.success(processDefinitions);
    }


}
