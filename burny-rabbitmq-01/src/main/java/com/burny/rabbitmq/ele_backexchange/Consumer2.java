package com.burny.rabbitmq.ele_backexchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Note 备份交换机报警队列
 * @Author cyx
 * @Date 2022/9/7 11:05
 */

@Slf4j
@Component
public class Consumer2 {


    @RabbitListener(queues = Info.back_warn_queue)
    public void receiW(Message message) {
        log.warn("备份队列之报警队列收到信息：{}", new String(message.getBody()));
    }

    @RabbitListener(queues = Info.confirm_queue)
    public void confirm(Message properties) {
        log.info("消费者:接收到的内容:{},优先级为:{}", new String(properties.getBody()), String.valueOf(properties.getMessageProperties().getPriority()), StandardCharsets.UTF_8);
    }

    @RabbitListener(queues = Info.back_queue)
    public void back(Message properties) {
        log.info("备份队列之普通队列收到消息:{}", new String(properties.getBody(), StandardCharsets.UTF_8));
    }
}
