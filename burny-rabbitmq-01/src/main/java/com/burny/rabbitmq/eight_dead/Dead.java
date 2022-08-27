package com.burny.rabbitmq.eight_dead;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/22 1:59
 */

@Slf4j
public class Dead {

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = Info.getC();
        channel.basicConsume(Info.dead_queue_name, false, ((message, delivery) -> {
            log.info(Info.pre + new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }), (consumerTag -> {
            log.info(Info.callback + new String(consumerTag));
        }));
    }
}
