package com.burny.rabbitmq.ten_confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
        //绑定id
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("id");
        log.info("生产者:发送内容:{}", data);
        data = "生产者:" + data;
        //发送正确
        rabbitTemplate.convertAndSend(Info.busi_exchange, Info.busi_exchange_to_busi_quque, data, correlationData);
        //错误的交换机 结果: 交换机确认回调:
        //2022-08-28 13:57:24.607  INFO 24612 --- [nectionFactory6] c.b.r.ten_confirm.ExchangeCallBack       : 交换机确认回调:交换机已经收到消息并且处理失败,ID为id,原因:channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'c_busi_exchangeps' in vhost '/', class-id=60, method-id=40)
        rabbitTemplate.convertAndSend(Info.busi_exchange + "ps", Info.busi_exchange_to_busi_quque, data, correlationData);
        //错误的队列 (routingkey不同) 总结:只要到达交换机就确认
        //结果
        //2022-08-28 13:57:24.612  INFO 24612 --- [nectionFactory6] c.b.r.ten_confirm.ExchangeCallBack       : 交换机确认回调:交换机已经收到消息并且成功处理,ID为id
        rabbitTemplate.convertAndSend(Info.busi_exchange, Info.busi_exchange_to_busi_quque + "sss", data, correlationData);
        //}
    }


}
