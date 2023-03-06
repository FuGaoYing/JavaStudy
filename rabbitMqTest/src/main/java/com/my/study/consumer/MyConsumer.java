package com.my.study.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author: FGY
 * @Description: mq消费者
 * @Date: Created in 2023/3/6 18:35
 * @Version:
 */
@Component
public class MyConsumer {

    @RabbitListener(queues = "myQueue1")
    public void listen1(Message message, Channel channel) throws IOException {
        System.out.print("myQueue1监听到消息" + Arrays.toString(message.getBody()));
        // 消息推送给消费者时会携带deliveryTag 以便确认收到的是哪条消息
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            Thread.sleep(1000);
            // 手动确认消息
            channel.basicAck(deliveryTag,true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 重新发送
            channel.basicNack(deliveryTag,true,true);
        }
    }

    @RabbitListener(queues = "myQueue2")
    public void listen2(Message message, Channel channel) throws IOException {
        // 消息推送给消费者时会携带deliveryTag 以便确认收到的是哪条消息
        System.out.print("myQueue2监听到消息" + Arrays.toString(message.getBody()));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            Thread.sleep(1000);
            // 手动确认消息
            channel.basicAck(deliveryTag,true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 重新发送
            channel.basicNack(deliveryTag,true,true);
        }
    }
}
