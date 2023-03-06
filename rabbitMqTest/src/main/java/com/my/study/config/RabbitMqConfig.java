package com.my.study.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: FGY
 * @Description: rabbitMq配置类
 * @Date: Created in 2023/3/6 18:33
 * @Version:
 * withArguments 常用参数
 *              1、x-max-length： 用于声明队列最大容量
 *              2、x-max-length-bytes 消息容量限制,但是这个是靠队列大小（bytes）来达到限制。
 *              3、x-message-ttl 消息存活时间,创建queue时设置该参数可指定消息在该queue中待多久
 *              可根据x-dead-letter-routing-key和x-dead-letter-exchange生成可延迟的死信队列
 *              4、x-max-priority 消息优先级,创建queue时arguments可以使用x-max-priority参数声明优先级队列
 *              5、x-expires 存活时间
 *              6、x-dead-letter-exchange和x-dead-letter-routing-key
 *              会在x-message-ttl时间到期后把消息放到x-dead-letter-routing-key和x-dead-letter-exchange指定的队列中达到延迟队列的目的
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitTemplate myRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置投递失败策略 true返回客户端 false 自动删除
        rabbitTemplate.setMandatory(true);
        // 消息投递到交换机回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("数据-" + correlationData + "-" + ack+ "-" + cause);
            if (ack) {
                System.out.println("消息投递成功！");
            } else {
                System.out.println("消息投递失败！原因 ：" + cause);
            }
        });
        // 投递消息到队列失败回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("消息: " + message
                    + "\n" + "回应码: " + replyCode
                    + "\n" + "交换机: " + exchange
                    + "\n" + "路由键: " + routingKey
                    + "\n" + "回应信息" + replyText);
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue1() {
        return QueueBuilder.durable("myQueue1").build();
    }

    @Bean
    public Queue myQueue2() {
        return QueueBuilder.durable("myQueue2").build();
    }

    /**
     *  直连交换机
     *  有明确的Bindings信息和RoutKey信息,direct类型的交换机规则比较简单,
     *  他会把消息路由到BindingKey和RoutingKey完全匹配队列中
     * @return
     */
    @Bean
    public DirectExchange myDirectExchange() {
        return ExchangeBuilder.directExchange("myDirectExchange")
                // durable(true) 持久化
                .durable(true)
//                .alternate() 候补交换机
//                .autoDelete() 自动删除
//                .delayed()  设置延迟标志
//                .ignoreDeclarationExceptions()
//                .internal()
//                .suppressDeclaration()
//                .withArguments() 设置其他参数
                .build();
    }

    /**
     * 直连交换机绑定测试
     * @param queueName
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingQueueAndExchange1(@Qualifier("myQueue1") Queue queueName,
                                            @Qualifier("myDirectExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queueName).to(exchange).with("routingKey1");
    }
    @Bean
    public Binding bindingQueueAndExchange2(@Qualifier("myQueue2") Queue queueName,
                                            @Qualifier("myDirectExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queueName).to(exchange).with("routingKey2");
    }

    /**
     * 主题交换机,有明确的BindingKey和RoutingKey,它们都是以.分割的
     * 在Direct Exchange的基础上进行了增强.
     * 优化了RoutingKey的规则,新增了路由匹配规则,
     * 例如user.#(表示user.开头#表示后面有0个或者0个以上的单词都可以匹配),
     * user.(表示user.开头,*表示后面可以跟任意一个单词都可以匹配)
     * @return
     */
    @Bean
    public TopicExchange myTopicExchange() {
        // durable(true) 持久化
        return ExchangeBuilder.topicExchange("myTopicExchange").durable(true).build();
    }

    /**
     *  扇形交换机，采用广播模式，根据绑定的交换机，路由到与之对应的所有队列。
     *  一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上。很像子网广播，
     *  每台子网内的主机都获得了一份复制的消息。Fanout交换机转发消息是最快的。
     * @return
     */
    @Bean
    public FanoutExchange myFanoutExchange() {
        // durable(true) 持久化
        return ExchangeBuilder.fanoutExchange("myFanoutExchange").durable(true).build();
    }

    /**
     * 头交换机，不处理路由键。而是根据发送的消息内容中的headers属性进行匹配。
     * 在绑定Queue与Exchange时指定一组键值对；当消息发送到RabbitMQ时会取到该消息的headers与Exchange绑定时指定的键值对进行匹配；
     * 如果完全匹配则消息会路由到该队列，否则不会路由到该队列。headers属性是一个键值对，可以是Hashtable，键值对的值可以是任何类型。
     * 而fanout，direct，topic 的路由键都需要要字符串形式的。
     * @return
     */
    @Bean
    public HeadersExchange myHeadersExchange() {
        // durable(true) 持久化
        return ExchangeBuilder.headersExchange("myHeadersExchange").durable(true).build();
    }

}
