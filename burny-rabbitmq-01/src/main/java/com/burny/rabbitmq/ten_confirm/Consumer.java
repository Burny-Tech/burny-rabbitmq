package com.burny.rabbitmq.ten_confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/28 9:52
 */

@Component
@Slf4j
public class Consumer {


    @RabbitListener(queues = Info.busi_quque)
    public void confirm(Message properties) {
        log.info("接收到的内容:{}", new String(properties.getBody(), StandardCharsets.UTF_8));
    }
}
