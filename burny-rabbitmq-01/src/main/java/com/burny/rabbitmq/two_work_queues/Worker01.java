package com.burny.rabbitmq.two_work_queues;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/18 23:05
 */

@Slf4j
public class Worker01 {


    @SneakyThrows
    public static void main(String[] args) {
        log.info(UUID.randomUUID().toString());
        Channel channel = Info.getC();
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            log.info(Info.pre + new String(message.getBody()));
        };
        CancelCallback callback = (consumerTag) -> {
            log.info(Info.callback);
        };
        channel.basicConsume(Info.queue_name, true, deliverCallback, callback);
    }
}
