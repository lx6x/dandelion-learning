package org.dandelion.flowable.flowable.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class FlowViewerDTO implements Serializable {

    /**
     * 流程key
     */
    private String key;

    /**
     * 是否完成(已经审批)
     */
    private boolean completed;
}
