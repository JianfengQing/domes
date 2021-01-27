package com.lve.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 订阅模式–多个消费者监听不同的队列，但队列都绑定同一个交换机
 *
 * @author qingjianfeng
 * @date   2021-01-26
 * */

@Configuration
public class FanoutQueueConfig {
    /**
     * 声明队列名.
     */
    private final String fanout1 = "fanout_queue_1";

    private final String fanout2 = "fanout_queue_2";

    /**
     * 声明交换机的名字.
     */
    private final String fanoutExchange = "fanoutExchange";

    /**
     * 声明队列.
     *
     * @return
     */
    @Bean
    public Queue fanoutQueue1() {
        return new Queue(fanout1);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(fanout2);
    }

    /**
     * 声明交换机.
     */
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(fanoutExchange);
    }

    /**
     * 队列绑定交换机，也可在可视化工具中进行绑定.
     *
     * @return
     */
    @Bean
    public Binding bindingFanoutQueue1(Queue fanoutQueue1, FanoutExchange exchange) {
        return BindingBuilder.bind(fanoutQueue1).to(exchange);
    }

    @Bean
    public Binding bindingFanoutQueue2(Queue fanoutQueue2, FanoutExchange exchange) {
        return BindingBuilder.bind(fanoutQueue2).to(exchange);
    }
}
