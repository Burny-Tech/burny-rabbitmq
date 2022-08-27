package com.burny.rabbitmq.nine_lazy_queue;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/25 22:28
 */

@Configuration
public class TTLConfig {

    //
    ////普通交换机  普通队列1 普通队列2  死信交换机 死信队列  消费者
    //public static  final String  busi_exchange="busi_exchange";
    //public static  final String  busi_queue1="busi_queue1";
    //public static  final String  busi_queue2="busi_queue2";
    //路由: 普通交换机到普通队列的路由
    //public static  final String rt_b_ex_to_q1="XA";
    //public  static  final String rt_b_ex_to_q2="XB";
    //路由:普通队列到死信交换机的
    //public static final String rt_q1_to_d_ex="YD";
    //public static final String rt_q2_to_d_ex="YD";
    //死信交换机到死信队列的
    //public static final String rt_d_ex_to_dq="YD";
    //死信交换机  死信队列,具体见图所示
    //public static  final String  dead_exchange="dead_exchange";
    //public static  final String  dead_queue1="dead_queue1";

    //普通交换机
    @Bean(Info.busi_exchange)
    public DirectExchange busi_exchange() {
        return new DirectExchange(Info.busi_exchange);
    }


    //死信交换机
    @Bean(Info.dead_exchange)
    public DirectExchange dead_exchange() {
        return new DirectExchange(Info.dead_exchange);
    }


    //消息有效期（TTL）和队列有效期（expires）
    //普通队列
    @Bean(Info.busi_queue1)
    public Queue busi_queue1() {
        return QueueBuilder
                .durable(Info.busi_queue1)
                .ttl(10 * 1000)
                .deadLetterExchange(Info.dead_exchange)
                .deadLetterRoutingKey(Info.rt_q1_to_d_ex)
                .build();
    }

    @Bean(Info.busi_queue2)
    public Queue busi_queue2() {
        return QueueBuilder
                .durable(Info.busi_queue2)
                .ttl(40 * 1000)
                .deadLetterExchange(Info.dead_exchange)
                .deadLetterRoutingKey(Info.rt_q1_to_d_ex)
                .build();
    }

    @Bean(Info.dead_queue1)
    public Queue dead_queue1() {
        return QueueBuilder
                .durable(Info.dead_queue1)
                .build();
    }

    @Bean
    public Binding busi_queue12busi_exchange(@Qualifier(Info.busi_queue1) Queue busi_queue1, @Qualifier(Info.busi_exchange) DirectExchange busi_exchange) {
        return BindingBuilder.bind(busi_queue1).to(busi_exchange).with(Info.rt_b_ex_to_q1);
    }

    @Bean
    public Binding busi_queue22busi_exchange(@Qualifier(Info.busi_queue2) Queue busi_queue2, @Qualifier(Info.busi_exchange) DirectExchange busi_exchange) {
        return BindingBuilder.bind(busi_queue2).to(busi_exchange).with(Info.rt_b_ex_to_q2);
    }

    @Bean
    public Binding dead_queue12dead_exchange(@Qualifier(Info.dead_queue1) Queue dead_queue1, @Qualifier(Info.dead_exchange) DirectExchange dead_exchange) {
        return BindingBuilder.bind(dead_queue1).to(dead_exchange).with(Info.rt_d_ex_to_dq);
    }


}
