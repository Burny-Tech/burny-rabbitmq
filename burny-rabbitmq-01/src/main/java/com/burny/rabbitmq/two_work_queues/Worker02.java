package com.burny.rabbitmq.two_work_queues;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/18 23:05
 */

@Slf4j
public class Worker02 {


    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = Info.getC();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //log.info("noSleep");
            log.info(Info.pre + new String(delivery.getBody()));
            //long deliveryTag = delivery.getEnvelope().getDeliveryTag();
            //log.info(String.valueOf(deliveryTag));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback callback = (consumerTag) -> {
            log.info(Info.callback);
        };
        channel.basicQos(8);

        channel.basicConsume(Info.queue_name, false, deliverCallback, callback);

    }
}
