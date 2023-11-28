package org.dandelion.flowable.flowable.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author lx6x
 * @date 2023/8/25
 */
@Data
@Schema(name = "流程模型视图对象")
public class ModelVo {
    /**
     * 模型ID
     */
    @Schema(name = "模型ID")
    private String modelId;
    /**
     * 模型名称
     */
    @Schema(name = "模型名称")
    private String modelName;
    /**
     * 模型Key
     */
    @Schema(name = "模型Key")
    private String modelKey;
    /**
     * 分类编码
     */
    @Schema(name = "分类编码")
    private String category;
    /**
     * 版本
     */
    @Schema(name = "版本")
    private Integer version;
    /**
     * 表单类型
     */
    @Schema(name = "表单类型")
    private Integer formType;
    /**
     * 表单ID
     */
    @Schema(name = "表单ID")
    private Long formId;
    /**
     * 模型描述
     */
    @Schema(name = "模型描述")
    private String description;
    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private Date createTime;
    /**
     * 流程xml
     */
    @Schema(name = "流程xml")
    private String bpmnXml;
    /**
     * 表单内容
     */
    @Schema(name = "表单内容")
    private String content;
}
