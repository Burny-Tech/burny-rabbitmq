package com.burny.rabbitmq.eight_dead;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/21 15:35
 */
@Slf4j
public class Consumer01 {
    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = Info.getC();

        //声明死信交换机
        //生命死信交换机,死信交换机类型;是否持久化,是否自动删除,是否是内部的,参数
        channel.exchangeDeclare(Info.dead_exchange_name, BuiltinExchangeType.DIRECT, true, false, true, null);
        channel.queueDeclare(Info.dead_queue_name, false, false, false, null);
        channel.queueBind(Info.dead_queue_name, Info.dead_exchange_name, Info.dead_routing_key, null);
        //消费端队列 设置routingkey
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", Info.dead_exchange_name);
        map.put("x-dead-letter-routing-key", Info.dead_routing_key);
        //成为死信队列2 原因：设置最大长度
        map.put("x-max-length", 10);
        //过期时间可以由生产端指定,只可以设置一次,如果生产端设置的话可以设置多次
        //map.put("x-message-ttl",10000);
        //new BasicProperties() 提供另外一种方式设置属性
        //业务消费端队列
        channel.queueDeclare(Info.queue_name, false, false, false, map);
        //消费端队列与业务交换机绑定
        channel.queueBind(Info.queue_name, Info.exchange_name, Info.dead_routing_key, null);

        channel.basicConsume(Info.queue_name, false, ((message, delivery) -> {
            //拒绝收消息
            //参数:标记,重新放入生产者队列
            channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
            log.info(Info.pre + new String(delivery.getBody()));
            //接收消息
            //参数:标记,批量应答
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }), (consumerTag -> {
            log.info(Info.callback + new String(consumerTag));
        }));
    }
}
