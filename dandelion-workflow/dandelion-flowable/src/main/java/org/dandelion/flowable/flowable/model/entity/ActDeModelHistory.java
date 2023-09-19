package org.dandelion.flowable.flowable.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 流程模型历史
 * </p>
 *
 * @author lx6x
 * @since 2023/09/18
 */
@Getter
@Setter
@TableName("act_de_model_history")
@Schema(name = "ActDeModelHistory", description = "流程模型历史")
public class ActDeModelHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String modelKey;

    private String description;

    private String modelComment;

    private LocalDateTime created;

    private String createdBy;

    private LocalDateTime lastUpdated;

    private String lastUpdatedBy;

    private LocalDateTime removalDate;

    private Integer version;

    private String modelEditorJson;

    private String modelId;

    private Integer modelType;

    private String tenantId;
}
