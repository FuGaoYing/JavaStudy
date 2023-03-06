package com.my.study.producer;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: FGY
 * @Description: mq生产者
 * @Date: Created in 2023/3/6 18:36
 * @Version:
 */
@Component
public class MyProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 生产者发送消息
     * @param exchange 交换机
     * @param routingKey  路由key
     * @param message 消息
     */
    public Object send(String exchange,
                       String routingKey,
                       Message message) {

        System.out.println("开始发送消息 : " + message);
        Object o = rabbitTemplate.convertSendAndReceive(exchange, routingKey, message, getCorrelationData());
        System.out.println("结束发送消息 : " + message);
        return o;
    }

    /**
     *  发送 string message
     * @param exchange 交换机
     * @param routingKey  队列与交换机绑定key
     * @param message 消息内容
     */
    public void send(String exchange,
                     String routingKey,
                     String message) {
        System.out.println("开始发送消息 : " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, getCorrelationData());
        System.out.println("结束发送消息 : " + message);
    }

    public void send(String routingKey, String message){
        rabbitTemplate.convertAndSend(routingKey, message);
    }

    /**
     *
     * @param exchange 交换机
     * @param routingKey 交换机绑定队列key
     * @param message 消息
     * @param messagePostProcessor 消息后置处理
     * @param correlationData 关联确认消息 在返回ack时一起发送区分消息
     */
    public void  send(String exchange, String routingKey, String message, MessagePostProcessor messagePostProcessor, CorrelationData correlationData){
        new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("2000");
                return message;
            }

            @Override
            public Message postProcessMessage(Message message, Correlation correlation) {
                return null;
            }
        };
        rabbitTemplate.convertSendAndReceive(exchange,routingKey, message,messagePostProcessor,correlationData);
    }

    /**
     * 消息唯一标识
     * @return
     */
    private static CorrelationData getCorrelationData() {
        return new CorrelationData(UUID.randomUUID().toString());
    }
}
