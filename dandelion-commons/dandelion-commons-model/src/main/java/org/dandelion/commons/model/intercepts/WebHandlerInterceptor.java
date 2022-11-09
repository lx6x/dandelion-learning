package org.dandelion.commons.model.intercepts;

import org.dandelion.commons.model.annotation.PermissionLimit;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO 请求拦截
 *
 * @author L
 * @version 1.0
 * @date 2021/11/3 11:43
 */
@Configuration
public class WebHandlerInterceptor implements HandlerInterceptor {

    /**
     * 忽略拦截的 url
     */
    private final String[] urls = {"/", "/doc.html", "/error", "/search.js"};

    /**
     * 预处理回调方法：在业务处理器处理请求之前被调用，预处理，进行编码，做安全控制，权限校验等
     *
     * @param request  请求
     * @param response 响应返回
     * @param handler  请求接口路径及方法
     * @return boolean ( true 继续流程 / false 结束流程直接返回)
     * @author L
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer requestUrl = request.getRequestURL();
        String requestUri = request.getRequestURI();

        for (String item : this.urls) {
            if (item.equals(requestUri)) {
                return true;
            }
        }
        /*

          String token = request.getHeader("token");
          if (null == token) {
            throw new GlobalException(ResultCode.FAIL);
          }
         */
        HandlerMethod handlerMethod = null;
        try {
            handlerMethod = (HandlerMethod) handler;
        } catch (Exception e) {
            e.printStackTrace();
        }
        PermissionLimit permissionLimit = handlerMethod.getMethodAnnotation(PermissionLimit.class);
        if (null != permissionLimit) {
            System.out.println("========> PermissionLimit 自定义权限验证 注解不为空");
            boolean limit = permissionLimit.limit();
            boolean admin = permissionLimit.admin();
        } else {
            System.out.println("========> PermissionLimit 自定义权限验证 注解为空");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 后处理回调方法，实现处理器的后处理，但在渲染 view 之前
     *
     * @param request      请求
     * @param response     响应返回
     * @param handler      .
     * @param modelAndView 模型视图对象
     * @author L
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
    }

    /**
     * 整个请求处理完毕后回调方法，及上一个方法完毕后回调，如程序的结束时间可以在此输出，类似 try 中的 finally，但仅调用处理器执行链中
     *
     * @param request  请求
     * @param response 响应返回
     * @param handler  .
     * @param ex       异常
     * @author L
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
    }
}
