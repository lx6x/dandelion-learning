package org.dandelion.flowable.flowable.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@Data
@TableName("ACT_DE_MODEL")
@Schema(name = "ActDeModelDO", description = "模型表DO")
public class ActDeModel implements Serializable {

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

    private Integer version;

    private String modelEditorJson;

    private byte[] thumbnail;

    private Integer modelType;

    private String tenantId;
}
