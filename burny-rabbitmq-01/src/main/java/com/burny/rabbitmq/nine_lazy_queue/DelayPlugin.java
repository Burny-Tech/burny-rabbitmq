package com.burny.rabbitmq.nine_lazy_queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/28 0:34
 */

@Configuration
public class DelayPlugin {

    //自定义交换机  -延迟交换机
    @Bean(Info.delay_exchange_name)
    public CustomExchange delay_exchange_name() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-delayed-type", "direct");
        //String name, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new CustomExchange(Info.delay_exchange_name, "x-delayed-message", false, false, map);
    }

    @Bean(Info.delay_queue_name)
    public Queue delay_queue_name() {
        return new Queue(Info.delay_queue_name);
    }

    @Bean
    public Binding delay_exchange_name_to_delay_queue_name(@Qualifier(Info.delay_queue_name) Queue queue, @Qualifier(Info.delay_exchange_name) CustomExchange customExchange) {

        return BindingBuilder.bind(queue).to(customExchange).with(Info.rt_delay_exchange_name_to_delay_queue_name).noargs();

    }
}
