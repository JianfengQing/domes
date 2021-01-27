package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    /**
     * consul 健康检查
     * */
    @GetMapping("/health")
    public String Health() {
        System.out.println("health 心跳正常......");
        return "OK";
    }
}
