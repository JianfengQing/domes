package com.lue.risk.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * feign 调用demo
 *
 * @author qingjianfeng
 * @date   2021-01-28
 * */
@Component
@FeignClient("server-product")
public interface TestDemoFeign {

    @GetMapping(value = "/test")
    public String test();
}
