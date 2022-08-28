package com.burny.rabbitmq.ten_confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Note 继承内部接口需要将该接口注入到rabbitmq里
 * @Author cyx
 * @Date 2022/8/28 10:44
 */
@Slf4j
@Configuration
public class ExchangeCallBack implements RabbitTemplate.ConfirmCallback {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 交换机确认回调
     * 1.发送消息 交换机接收到了并且成功处理了 回调
     * 参数
     * 保存毁掉消息的ID以及相关信息
     * 交换机收到消息 true
     * case null
     * <p>
     * 2. 发送消息 交换机接收 但处理失败 回调
     * 参数
     * 保存毁掉消息的ID以及相关信息
     * fasle
     * 错误原因
     */

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if (ack) {
            log.info("交换机确认回调:交换机已经收到消息并且成功处理,ID为{}", correlationData != null ? correlationData.getId() : "");
        } else {
            log.info("交换机确认回调:交换机已经收到消息并且处理失败,ID为{},原因:{}", correlationData != null ? correlationData.getId() : "", cause);
        }
    }
}
