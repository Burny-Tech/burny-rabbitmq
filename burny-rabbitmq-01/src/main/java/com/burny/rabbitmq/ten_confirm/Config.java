package com.burny.rabbitmq.ten_confirm;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/28 9:51
 */
@Slf4j
@Configuration
public class Config {

    @Bean(Info.busi_exchange)
    public DirectExchange c_busi_exchange() {
        return new DirectExchange(Info.busi_exchange);
    }

    @Bean(Info.busi_quque)
    public Queue c_busi_quque() {
        return QueueBuilder.durable(Info.busi_quque).build();
    }

    @Bean
    public Binding busi_exchange_to_busi_quque(@Qualifier(Info.busi_quque) Queue busi_quque, @Qualifier(Info.busi_exchange) DirectExchange busi_exchange) {
        return BindingBuilder.bind(busi_quque).to(busi_exchange).with(Info.busi_exchange_to_busi_quque);
    }
}
