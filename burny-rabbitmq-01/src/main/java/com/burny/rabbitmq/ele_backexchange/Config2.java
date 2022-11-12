package com.burny.rabbitmq.ele_backexchange;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/9/1 16:41
 */
@Configuration
public class Config2 {

    @Bean(Info.back_exchange)
    public FanoutExchange back_exchange() {
        return new FanoutExchange(Info.back_exchange);
    }

    @Bean(Info.confirm_exchange)
    public DirectExchange confirm_exchange() {
        return ExchangeBuilder.directExchange(Info.confirm_exchange).durable(false).withArgument("alternate-exchange", Info.back_exchange).build();
        //return new DirectExchange(Info.confirm_exchange);
    }

    @Bean(Info.back_queue)
    public Queue back_queue() {
        //return QueueBuilder.durable(Info.back_queue).build();
        return QueueBuilder.durable(Info.back_queue).lazy().build();
    }

    @Bean(Info.confirm_queue)
    public Queue confirm_queue() {
        return QueueBuilder.durable(Info.confirm_queue).maxPriority(100).build();
        //return QueueBuilder.durable(Info.confirm_queue).build();
    }

    @Bean(Info.back_warn_queue)
    public Queue back_warn_queue() {
        return QueueBuilder.durable(Info.back_warn_queue).build();
    }

    @Bean
    public Binding back_queue_to_back_exchange(@Qualifier(Info.back_queue) Queue back_queue
            , @Qualifier(Info.back_exchange) FanoutExchange back_exchange) {
        return BindingBuilder.bind(back_queue).to(back_exchange);
    }

    @Bean
    public Binding confirm_queue_to_confirm_exchange(@Qualifier(Info.confirm_queue) Queue confirm_queue
            , @Qualifier(Info.confirm_exchange) DirectExchange confirm_exchange) {
        return BindingBuilder.bind(confirm_queue).to(confirm_exchange).with(Info.rt_confirm_exchange_confirm_queue);
    }

    @Bean
    public Binding back_warn_queue_to_back_exchange(@Qualifier(Info.back_warn_queue) Queue back_warn_queue
            , @Qualifier(Info.back_exchange) FanoutExchange back_exchange) {
        return BindingBuilder.bind(back_warn_queue).to(back_exchange);
    }


}
