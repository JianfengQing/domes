package com.lue.risk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * consul 健康检查
 *
 * @author qingjianfeng
 * @date   2021-01-28
 * */
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
