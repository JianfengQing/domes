package com.lve.risk.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由模式
 *
 * @author qingjianfeng
 * @date   2021-01-26
 * */

@Configuration
public class TopicQueueConfig {
    /**
     * 声明队列名.
     */
    private final String topic1 = "topic_queue_1";

    private final String topic2 = "topic_queue_2";

    /**
     * 声明交换机的名字.
     */
    private final String topicExchange = "topicExchange";

    /**
     * 声明队列.
     *
     * @return
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(topic1);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(topic2);
    }

    /**
     * 声明路由交换机.
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    /**
     * 队列绑定交换机,指定routingKey,也可在可视化工具中进行绑定.
     *
     * @return
     */
    @Bean
    Binding bindingTopicExchange1(Queue topicQueue1, TopicExchange exchange) {
        return BindingBuilder.bind(topicQueue1).to(exchange).with("topic.keyA");
    }

    /**
     * 队列绑定交换机,指定routingKey,也可在可视化工具中进行绑定.
     * 绑定的routing key 也可以使用通配符：
     * *：匹配不多不少一个词
     * #：匹配一个或多个词
     *
     * @return
     */
    @Bean
    Binding bindingTopicExchange2(Queue topicQueue2, TopicExchange exchange) {
        return BindingBuilder.bind(topicQueue2).to(exchange).with("topic.#");
    }
}
