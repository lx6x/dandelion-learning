package org.dandelion.data.routing.source.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dandelion.data.routing.source.datasource.DynamicDataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/06/09 21:33
 */
@Aspect
@Component
public class GlobalDataSourceAspect {

    @Autowired
    private HttpSession session;

    @Pointcut("execution(* org.dandelion.data.routing.source.service.DUserDelService.def())")
    public void globalPointcut() {

    }

    @Around("globalPointcut()")
    public Object around(ProceedingJoinPoint point) {
        DynamicDataSourceContextHolder.setDataSourceType((String) session.getAttribute("session_key"));
        try {
            return point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        return null;
    }
}
