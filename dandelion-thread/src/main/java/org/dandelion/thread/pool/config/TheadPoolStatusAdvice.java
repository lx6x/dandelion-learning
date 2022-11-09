package org.dandelion.thread.pool.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/12/17 10:23
 */
@Aspect
@Component
public class TheadPoolStatusAdvice {

    private static final Logger logger = LoggerFactory.getLogger(TheadPoolStatusAdvice.class);

    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Pointcut("@annotation(org.dandelion.thread.pool.config.ThreadPoolStatus)")
    public void threadPoolStatus() {
    }


    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };


    @Around("threadPoolStatus()")
    public Object threadPoolDoing(ProceedingJoinPoint point) throws Throwable {
        Object target = point.getTarget();
        Class<?> aClass = target.getClass();
        String name = aClass.getName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                //获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
            }
        }
        //获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = Class.forName(name).getMethod(methodName, classes);
        ThreadPoolStatus annotation = method.getAnnotation(ThreadPoolStatus.class);
        showThreadPoolInfo(annotation.value());
        return point.proceed();
    }


    private void showThreadPoolInfo(String prefix) {
        ThreadPoolExecutor threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();
        if (null == threadPoolExecutor) {
            return;
        }
        logger.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                threadPoolTaskExecutor.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }
}
