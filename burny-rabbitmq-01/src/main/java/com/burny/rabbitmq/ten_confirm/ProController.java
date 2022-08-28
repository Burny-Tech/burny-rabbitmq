package com.burny.rabbitmq.ten_confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/28 9:51
 */
@Slf4j
@RestController
@RequestMapping("/c")
public class ProController {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/{data}")
    public void pro(@PathVariable String data) {
        //for (int i = 0; i < 100; i++) {
        log.info("发送内容:{}", data);
        rabbitTemplate.convertAndSend(Info.busi_exchange, Info.busi_exchange_to_busi_quque, data);
        //}
    }


}
