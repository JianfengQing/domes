package com.lve.risk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(HealthController.class);

    /**
     * consul 健康检查
     * */
    @GetMapping("/health")
    public String Health() {
        logger.info("----------------------------------------------RiskService在线----------------------------------------");
        return "OK";
    }
}
