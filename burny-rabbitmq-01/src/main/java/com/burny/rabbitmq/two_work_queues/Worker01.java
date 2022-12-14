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
public class Worker01 {


    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = Info.getC();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            log.info(Info.pre + new String(delivery.getBody()));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //long deliveryTag = delivery.getEnvelope().getDeliveryTag();
            //log.info(String.valueOf(deliveryTag));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback callback = (consumerTag) -> {
            log.info(Info.callback);
        };
        //设置成不公平分发,即能者多劳,
        channel.basicQos(1);
        //按比例分发,只有存在客户端迟迟未应答才会生效
        channel.basicQos(2);

        channel.basicConsume(Info.queue_name, false, deliverCallback, callback);

    }
}
