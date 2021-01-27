package com.example.demo.consumer;

import com.example.demo.constant.AMQPConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SimpleConsumer {

    @RabbitListener(queues = AMQPConstant.SIMPLE_QUEUE_NAME)
    public void readMessage(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        System.out.println("读取消费的消息:" + new String(message.getBody()));
    }
}
