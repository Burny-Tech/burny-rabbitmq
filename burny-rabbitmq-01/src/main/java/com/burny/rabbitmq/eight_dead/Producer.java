package com.burny.rabbitmq.eight_dead;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/22 1:04
 */
@Slf4j
public class Producer {

    @SneakyThrows
    public static void main(String[] args) {
        //如果发送前需要确保消费者已经处于监听状态,否则消息会丢失
        Channel channel = Info.getC();
        //交换机名字；交换机类型；是否持久化；是否自动删除，参数
        channel.exchangeDeclare(Info.exchange_name, BuiltinExchangeType.DIRECT, true, false, null);
        ConfirmListener confirmListener1 = new ConfirmListener() {
            //仅仅用于
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                log.info("消息已被接收,deliveryTag:{}", deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                log.info("消息已经被接拒收,deliveryTag:{}", deliveryTag);
            }
        };
        channel.addConfirmListener(confirmListener1);
        channel.confirmSelect();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("1").build();
        String s = new String("我是发送内容");
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(Info.exchange_name, Info.dead_routing_key, properties, (s).getBytes(StandardCharsets.UTF_8));
            log.info("发送次数" + i + ";发送内容:" + (new String(s)) + ";下一次发送的deliveryTag" + channel.getNextPublishSeqNo());
        }


    }

}
