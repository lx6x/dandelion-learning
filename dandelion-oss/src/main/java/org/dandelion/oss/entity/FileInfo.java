package org.dandelion.oss.entity;

import java.io.Serializable;

/**
 * file_info
 *
 * @author L
 */
public class FileInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 业务类型（1：oss）
     */
    private Integer bizeType;

    private static final long serialVersionUID = 1L;
}