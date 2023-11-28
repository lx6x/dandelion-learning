package org.dandelion.flowable.flowable.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lx6x
 * @date 2023/8/25
 */
@Data
@Schema(name = "DeployVo", description = "流程部署视图对象")
public class DeployVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    @Schema(name = "流程定义ID")
    private String definitionId;

    /**
     * 流程名称
     */
    @Schema(name = "流程名称")
    private String processName;

    /**
     * 流程Key
     */
    @Schema(name = "流程Key")
    private String processKey;

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
     * 表单ID
     */
    @Schema(name = "表单ID")
    private Long formId;

    /**
     * 表单名称
     */
    @Schema(name = "表单名称")
    private String formName;

    /**
     * 部署ID
     */
    @Schema(name = "部署ID")
    private String deploymentId;

    /**
     * 流程定义状态: 1:激活 , 2:中止
     */
    @Schema(name = "流程定义状态: 1:激活 , 2:中止")
    private Boolean suspended;

    /**
     * 部署时间
     */
    @Schema(name = "部署时间")
    private Date deploymentTime;
}
