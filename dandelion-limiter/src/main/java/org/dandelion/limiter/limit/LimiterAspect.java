package org.dandelion.limiter.limit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.dandelion.limiter.annotation.LimiterAnnotation;
import org.dandelion.limiter.common.GlobalException;
import org.dandelion.limiter.utils.HttpUtil;
import org.dandelion.limiter.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * TODO limiting aspect
 *
 * @author L
 * @version 1.0
 * @date 2022/6/7 11:30
 */
@Aspect
@Component
public class LimiterAspect {

    private static final Logger logger = LoggerFactory.getLogger(LimiterAspect.class);

    @Autowired
    private RedisUtils redisUtils;

    @Before("@annotation(limiterAnnotation)")
    public void doBefore(JoinPoint joinpoint, LimiterAnnotation limiterAnnotation) throws Throwable {
        String key = limiterAnnotation.key();
        LimiterType limiterType = limiterAnnotation.limitType();
        int count = limiterAnnotation.count();
        int time = limiterAnnotation.time();

        StringBuilder sb = new StringBuilder();
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> aClass = method.getDeclaringClass();
        String name = method.getName();
        sb.append(aClass).append("-").append(name);

        if (LimiterType.IP == limiterType) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            HttpServletRequest request = requestAttributes.getRequest();
            String ipAddress = HttpUtil.getIpAddress(request);
            sb.append("-").append(ipAddress);
        }

        Integer redis = (Integer) redisUtils.get(sb.toString());
        logger.info("当前 【{}】 方法访问次数 【{}】", sb, redis);
        if (null == redis) {
            redisUtils.incr(sb.toString(), 1);
            redisUtils.expire(sb.toString(), time);
        } else {
            if (redis >= count) {
                throw new GlobalException("访问过于频繁，请稍候再试");
            }
            redisUtils.incr(sb.toString(), 1);
        }

    }

}
