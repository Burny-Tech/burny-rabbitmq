package com.burny.rabbitmq.six_direct;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/21 15:35
 */

public class Consumer02 {
    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = Info.getC();
        //生成临时队列,队列名称是随机的,当消费者断开与队列的连接,则队列会自动删除.
        String queue = channel.queueDeclare().getQueue();
        //绑定交换机与队列
        channel.queueBind(queue, Info.exchange_name, "error", null);

        channel.basicConsume(queue, true, ((consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        }), (consumerTag -> {
            System.out.println(new String(consumerTag));
        }));
    }
}
