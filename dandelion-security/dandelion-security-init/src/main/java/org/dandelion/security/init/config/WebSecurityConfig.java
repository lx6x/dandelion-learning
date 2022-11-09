package org.dandelion.security.init.config;

import org.dandelion.security.init.handlers.LoginFailureHandler;
import org.dandelion.security.init.handlers.LoginSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * TODO 自定一配置
 *      继承 WebSecurityConfigurerAdapter
 *
 * @author L
 * @version 1.0
 * @date 2022/2/28 17:15
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    /**
     * 认证管理器配置方法，配置认证管理器 AuthenticationManager，身份认证接口
     *
     * @param auth .
     * @author L
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        String password = passwordEncoder().encode("123456");
        logger.info("加密后的密码：{}", password);

        // 访问权限设置
//        auth.inMemoryAuthentication().withUser("user").password(password).roles("ADMIN").and();
        auth.inMemoryAuthentication().withUser("user").password(password).roles("USER").and();
    }

    /**
     * 核心过滤器配置方
     *
     * @param web .
     * @author L
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 安全过滤器链配置方法
     *
     * @param http .
     * @author L
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test")  // 设置 controller 请求路径
                .permitAll()  // 允许任何人访问
                .antMatchers("/test1").hasRole("ADMIN") // 指定权限为ADMIN才能访问
                .antMatchers("/test2").hasAnyRole("ADMIN", "USER") //指定多个权限都能访问
                .anyRequest()  // 任何其他请求
                .authenticated()  // 都需要身份验证
                .and()          // 结束标签
                .formLogin()  //使用表单认证方式
                .loginProcessingUrl("/login")  //配置默认登录入口
                .successHandler(loginSuccessHandler)    // 使用自定义成功结果处理器
                .failureHandler(loginFailureHandler)    // 使用自定义失败结果处理器
                .and()
                .csrf().disable()   // 关闭跨站请求伪造保护
        ;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
