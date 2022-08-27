package com.burny.rabbitmq.six_direct;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/21 15:35
 */

public class Consumer01 {
    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = Info.getC();
        String queue = channel.queueDeclare().getQueue();
        //绑定交换机与队列
        channel.queueBind(queue, Info.exchange_name, "info", null);
        channel.queueBind(queue, Info.exchange_name, "warn", null);


        channel.basicConsume(queue, false, ((message, delivery) -> {
            System.out.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }), (consumerTag -> {
            System.out.println(new String(consumerTag));
        }));
    }
}
