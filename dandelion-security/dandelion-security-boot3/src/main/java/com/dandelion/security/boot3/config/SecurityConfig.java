package com.dandelion.security.boot3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author lx6x
 * @date 2023/10/24
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * 安全过滤器使用 SecurityFilterChain API 插入到 FilterChainProxy 中。
     * 这些过滤器可用于许多不同的目的，例如身份验证、授权、漏洞利用保护等。 筛选器按特定顺序执行，以确保在正确的时间调用它们，例如，应在执行授权之前调用执行身份验证的 。
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 调用 是为了防止 CSRF 攻击。CsrfFilter
                .csrf(Customizer.withDefaults())
                // 调用身份验证筛选器对请求进行身份验证。
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

}
