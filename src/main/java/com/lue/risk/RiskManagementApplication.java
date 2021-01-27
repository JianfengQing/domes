package com.lue.risk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.lue.risk.controller",
        "com.lue.risk.service",
        "com.lue.risk.config",
        "com.lue.risk.utils",
        "com.lue.risk.consumer",
        "com.lue.risk.exception"})
@MapperScan("com.lue.risk.mapper")
@EnableDiscoveryClient
public class RiskManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiskManagementApplication.class, args);
    }
}
