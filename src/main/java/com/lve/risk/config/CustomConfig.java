package com.lve.risk.config;

import com.lve.risk.interceptor.CrossInterceptor;
import com.lve.risk.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
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
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/health");
    }

    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
}
