package com.lve.risk.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工作队列（1生产者对多个消费者）
 *
 * @author qingjainfeng
 * @date   2021-01-26
 * */

@Configuration
public class WorkQueueConfig {
    /**队列名 */
    private final String work = "work_queue";

    @Bean
    public Queue workQueue() {
        return new Queue(work);
    }
}
