package com.lve.risk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.lve.risk.controller",
        "com.lve.risk.service",
        "com.lve.risk.config",
        "com.lve.risk.utils",
        "com.lve.risk.consumer",
        "com.lve.risk.exception",
        "com.lve.risk.interceptor",
        "com.lve.risk.feign",
        "com.lve.risk.aop"})
@MapperScan("com.lve.risk.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class RiskManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiskManagementApplication.class, args);
    }
}
