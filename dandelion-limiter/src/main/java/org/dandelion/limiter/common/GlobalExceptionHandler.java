package org.dandelion.limiter.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 全局异常处理 捕获
 *
 * @author L
 * @version 1.0
 * @date 2021/10/25 14:44
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param req .
     * @param e   .
     * @return 自定义返回
     */
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public CommonResult<String> globalException(HttpServletRequest req, GlobalException e) {
        logger.error("## 发生业务异常！原因是：{}", e.getErrorMessage());
        return CommonResult.fail(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * 处理空指针的异常
     *
     * @param req .
     * @param e   .
     * @return 自定义返回
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public CommonResult<String> exceptionHandler(HttpServletRequest req, NullPointerException e) {
        logger.error("## 空指针异常！原因是: ", e);
        return CommonResult.fail(ResultCode.FAIL.getMessage());
    }

    /**
     * 处理违反唯一键的约束
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public CommonResult<String> sqlExceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("## SQL异常！原因是: ", e);
        return CommonResult.fail(ResultCode.FAIL.getCode(), "唯一，不可重复");
    }

    /**
     * 处理其他异常
     *
     * @param req .
     * @param e   .
     * @return 自定义返回
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult<String> exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("## 未知异常！原因是: ", e);
        return CommonResult.fail(ResultCode.FAIL.getMessage());
    }

}
