package org.dandelion.limiter.common;

/**
 * TODO 自定义全局异常
 *
 * @author L
 * @version 1.0
 * @date 2021/10/25 14:40
 */
public class GlobalException extends RuntimeException {

    /**
     * 错误状态码
     */
    private int errorCode;
    /**
     * 错误信息
     */
    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public GlobalException() {
        super();
    }

    public GlobalException(IResultCode iResultCode) {
        super(iResultCode.getMessage());
        this.errorCode = iResultCode.getCode();
        this.errorMessage = iResultCode.getMessage();
    }

    public GlobalException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ResultCode.FAIL.getCode();
        this.errorMessage = errorMessage;
    }
}
