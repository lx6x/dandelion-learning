package org.dandelion.repeat.submit.common;

/**
 * TODO 状态接口
 *
 * @author L
 * @version 1.0
 * @date 2022/06/15 10:11
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
