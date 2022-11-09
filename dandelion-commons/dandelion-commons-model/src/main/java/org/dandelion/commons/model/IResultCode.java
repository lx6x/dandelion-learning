package org.dandelion.commons.model;

/**
 * TODO 状态接口
 *
 * @author L
 * @version 1.0
 * @date 2021/10/15 11:19
 */
public interface IResultCode {

    /**
     * 状态码
     *
     * @return int
     * @author L
     */
    int getCode();

    /**
     * 描述信息
     *
     * @return string
     * @author L
     */
    String getMessage();
}
