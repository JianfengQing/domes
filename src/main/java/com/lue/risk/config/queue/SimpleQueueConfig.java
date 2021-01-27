package com.lue.risk.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  简单队列（1对1)
 *
 * @author qingjianfeng
 * @Date   2021-01-26
 * */

@Configuration
public class SimpleQueueConfig {

    private String simpleQueue = "queue_simple";

    @Bean
    public Queue simpleQueue(){
        return new Queue(simpleQueue);
    }
}
