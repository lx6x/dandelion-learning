package org.dandelion.flowable.flowable.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author liujunfei
 * @date 2023/7/14
 */
@Data
@Schema(name = "DeployModelDTO", description = "流程部署DTO")
public class DeployModelDTO {

    @Schema(description = "流程模型id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(description = "分类 例如，可以将具有相似业务功能或流程类型的流程定义放在同一个分类下")
    private String category;
}
