package com.example.demo.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * rabbitMQ 消息序列化配置
 *
 * @Data   2021-01-25
 * @author qingjianfeng
 * */

@Configuration
@EnableRabbit
public class AMQPConfig {
    /**
     * 设置自定义的 MessageConverter
     * 使用Jackson2JsonMessageConverter消息转换器
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
