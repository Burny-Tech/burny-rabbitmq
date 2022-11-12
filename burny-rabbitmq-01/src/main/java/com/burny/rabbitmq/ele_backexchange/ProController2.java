package com.burny.rabbitmq.ele_backexchange;


import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/9/7 10:59
 */

@RestController
@RequestMapping("/back")
public class ProController2 {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/{data}")
    public void pro(@PathVariable String data) {
        CorrelationData correlationData = new CorrelationData();
        //发送可达的信息
        rabbitTemplate.convertAndSend(Info.confirm_exchange, Info.rt_confirm_exchange_confirm_queue + "ddd", data, correlationData);
        //发送不可达的信息
        rabbitTemplate.convertAndSend(Info.confirm_exchange, Info.rt_confirm_exchange_confirm_queue, data, correlationData);

        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0) {
                org.springframework.amqp.core.MessagePostProcessor postProcessor = new org.springframework.amqp.core.MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setPriority(3);
                        return message;
                    }
                };
                rabbitTemplate.convertAndSend(Info.confirm_exchange, Info.rt_confirm_exchange_confirm_queue, data, postProcessor);
            } else if (i % 5 == 0) {
                org.springframework.amqp.core.MessagePostProcessor postProcessor = new org.springframework.amqp.core.MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setPriority(5);
                        return message;
                    }
                };

                rabbitTemplate.convertAndSend(Info.confirm_exchange, Info.rt_confirm_exchange_confirm_queue, data, postProcessor);

            } else {
                rabbitTemplate.convertAndSend(Info.confirm_exchange, Info.rt_confirm_exchange_confirm_queue, data);

            }

        }


    }


}
