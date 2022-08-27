package com.burny.rabbitmq.nine_lazy_queue;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/27 19:09
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 发送消息到达消费者
 */

@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{data}")
    public void sendMsg(@PathVariable(value = "data") String data) {
        log.info("当前时间{},发送一条消息给两个TTL队列:{}", LocalDateTime.now(), data);

        rabbitTemplate.convertAndSend(Info.busi_exchange, Info.rt_b_ex_to_q1, "10s" + data);
        rabbitTemplate.convertAndSend(Info.busi_exchange, Info.rt_b_ex_to_q2, "40s" + data);
    }

}