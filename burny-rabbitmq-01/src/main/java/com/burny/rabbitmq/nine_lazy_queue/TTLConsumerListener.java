package com.burny.rabbitmq.nine_lazy_queue;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/27 19:16
 */
@Slf4j
@Component
public class TTLConsumerListener {

    @RabbitListener(queues = Info.dead_queue1)
    public void receive(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("当前时间:{},获取消息:{}", LocalDateTime.now(), msg);
    }

    @RabbitListener(queues = Info.delay_queue_name)
    public void delay(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("类型:{},当前时间:{},获取消息:{}", "延迟插件", LocalDateTime.now(), msg);
    }
}
