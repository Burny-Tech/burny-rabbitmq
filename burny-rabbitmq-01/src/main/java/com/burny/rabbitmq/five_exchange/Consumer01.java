package com.burny.rabbitmq.five_exchange;

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
        //声明交换机
        //channel.exchangeDeclare(Info.exchange_name, BuiltinExchangeType.FANOUT,true,false,true,null);
        //生成临时队列,队列名称是随机的,当消费者断开与队列的连接,则队列会自动删除.
        String queue = channel.queueDeclare().getQueue();
        //绑定交换机与队列
        channel.queueBind(queue, Info.exchange_name, "", null);

        channel.basicConsume(queue, false, ((message, delivery) -> {
            System.out.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }), (consumerTag -> {
            System.out.println(new String(consumerTag));
        }));
    }
}
