package com.ws.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wusen
 * @date 2019/10/30 0030-下午 19:47
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**") //需要过滤的HTML
                .excludePathPatterns("/admin") //不需要过滤的
                .excludePathPatterns("/admin/login"); //不需要过滤的
    }
}
