package com.burny.rabbitmq.ten_confirm;

import lombok.extern.slf4j.Slf4j;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/28 9:51
 */
@Slf4j
public class Info {


    //如果是持久化队列或者交换机,每次更滑pre 方便开发,不需要每次都delete 掉交换机或者队列
    //public static  final  String  pre="a";

    public static final String busi_exchange = "c_busi_exchange";
    public static final String busi_quque = "c_busi_quque";
    public static final String busi_exchange_to_busi_quque = "c_busi_exchange_to_busi_quque";


}
