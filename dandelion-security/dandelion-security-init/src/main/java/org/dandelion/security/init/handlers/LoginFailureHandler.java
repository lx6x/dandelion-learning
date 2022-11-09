package org.dandelion.security.init.handlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO 登录失败触发，认证失败结果处理器
 *
 * @author L
 * @version 1.0
 * @date 2022/3/2 11:51
 */
@Component("LoginFailureHandler")
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        super.onAuthenticationFailure(request, response, exception);


        response.setContentType("application/json;charset=UTF-8");

        logger.info("登录失败...");

    }
}
