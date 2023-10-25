package com.dandelion.security.boot3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

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
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // 调用 是为了防止 CSRF 攻击。CsrfFilter
//                .csrf(Customizer.withDefaults())
//                // 调用身份验证筛选器对请求进行身份验证。
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(withDefaults())
                // 不需要 csrf
//                .csrf(AbstractHttpConfigurer::disable)
                // 基于token
//                .sessionManagement(session -> {
//                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                })
//                .authorizeHttpRequests(auth -> {
//                    auth
                            // 允许所有OPTIONS请求
//                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/").permitAll()
                            // 所有请求都会被拦截
//                            .anyRequest().authenticated();
//                });
        ;
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("123").build());
//        return manager;
//    }

    /**
     * 配置跨源访问(CORS)
     * @return
     */
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOriginPattern("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(source);
//    }

}
