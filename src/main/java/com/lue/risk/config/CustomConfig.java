package com.lue.risk.config;

import com.lue.risk.interceptor.CrossInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //跨域拦截
        registry.addInterceptor(new CrossInterceptor())
                .addPathPatterns("/**")                //拦截所有请求
                .excludePathPatterns("/health");       //不拦截consul 健康检查
    }
}
