package org.dandelion.flowable.flowable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.flowable.common.R;
import org.dandelion.flowable.flowable.converter.ActDeModelConverter;
import org.dandelion.flowable.flowable.model.vo.ActDeModelVO;
import org.dandelion.flowable.flowable.service.IActDeModelService;
import org.flowable.ui.modeler.rest.app.ModelBpmnResource;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "流程模型")
@RestController
@RequestMapping("/dandelion/model")
public class ModelController {

    @Resource
    private ModelBpmnResource modelBpmnResource;
    @Resource
    protected FlowableModelQueryService modelQueryService;
    @Resource
    private IActDeModelService iActDeModelService;
    @Resource
    private ActDeModelConverter actDeModelConverter;

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


}
