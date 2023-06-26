package org.dandelion.flowable.flowable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.flowable.ui.modeler.rest.app.ModelBpmnResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "流程模型相关")
@RestController
@RequestMapping("/dandelion/model")
public class ModelController {

    @Resource
    private ModelBpmnResource modelBpmnResource;

    /**
     * 导出model的xml文件
     */
    @Operation(summary  = "导出model的xml文件")
    @GetMapping("exportBpmnXml")
    @Parameters({
            @Parameter(name = "id",description = "流程模型id",in = ParameterIn.PATH)
    })
    public void exportBpmnXml(String id, HttpServletResponse response) throws Exception {
        modelBpmnResource.getProcessModelBpmn20Xml(response, id);
    }


}
