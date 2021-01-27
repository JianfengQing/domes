package com.example.demo.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由模式，订阅模式+指定routingKey
 *
 * @author qingjianfeng
 * @date   2021-01-26
 * */

@Configuration
public class DirectQueueConfig {
    /**
     * 声明队列名.
     */
    private final String direct1 = "direct_queue_1";

    private final String direct2 = "direct_queue_2";

    /**
     * 声明交换机的名字.
     */
    private final String directExchange = "directExchange";

    /**
     * 声明队列.
     *
     * @return
     */
    @Bean
    public Queue directQueue1() {
        return new Queue(direct1);
    }

    @Bean
    public Queue directQueue2() {
        return new Queue(direct2);
    }

    /**
     * 声明路由交换机.
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }

    /**
     * 队列绑定交换机,指定routingKey,也可在可视化工具中进行绑定.
     *
     * @return
     */
    @Bean
    Binding bindingDirectExchange1(Queue directQueue1, DirectExchange exchange) {
        return BindingBuilder.bind(directQueue1).to(exchange).with("update");
    }

    /**
     * 队列绑定交换机,指定routingKey,也可在可视化工具中进行绑定.
     *
     * @return
     */
    @Bean
    Binding bindingDirectExchange2(Queue directQueue2, DirectExchange exchange) {
        return BindingBuilder.bind(directQueue2).to(exchange).with("add");
    }

}
