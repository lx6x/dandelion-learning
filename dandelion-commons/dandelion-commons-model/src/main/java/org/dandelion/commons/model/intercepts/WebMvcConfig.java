package org.dandelion.commons.model.intercepts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO 请求拦截
 *
 * @author L
 * @version 1.0
 * @date 2021/11/3 11:40
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public WebHandlerInterceptor getWebHandlerInterceptor() {
        return new WebHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getWebHandlerInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("doc");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("*") //允许跨域的域名，可以用*表示允许任何域名使用
                    .allowedMethods("*") //允许任何方法（post、get等）
                    .allowedHeaders("*") //允许任何请求头
                    .allowCredentials(true) //带上cookie信息
//                        .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L) //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
                ;
            }
        };
    }
}
