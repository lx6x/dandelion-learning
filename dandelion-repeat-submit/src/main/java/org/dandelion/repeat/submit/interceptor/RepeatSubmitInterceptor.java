package org.dandelion.repeat.submit.interceptor;

import com.alibaba.fastjson.JSON;
import org.dandelion.repeat.submit.annotation.RepeatSubmit;
import org.dandelion.repeat.submit.common.CommonResult;
import org.dandelion.repeat.submit.utils.ServletUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * TODO 防止重复提交拦截器
 *
 * @author L
 * @version 1.0
 * @date 2022/06/15 10:36
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request, annotation)) {
                    CommonResult<String> fail = CommonResult.fail(annotation.message());
                    ServletUtils.renderString(response, JSON.toJSONString(fail));
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request    .
     * @param annotation .
     * @return boolean
     * @author L
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation);
}
