package org.dandelion.commons.model;

/**
 * TODO api状态码返回
 *
 * @author L
 * @version 1.0
 * @date 2021/10/15 11:21
 */
public enum ResultCode implements IResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 失败
     */
    FAIL(500, "FAIL");

    int code;
    String messages;

    ResultCode(int code, String messages) {
        this.code = code;
        this.messages = messages;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.messages;
    }
}
