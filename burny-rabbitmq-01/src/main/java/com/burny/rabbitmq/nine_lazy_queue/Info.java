package com.burny.rabbitmq.nine_lazy_queue;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/23 9:39
 */

public class Info {

    //普通交换机  普通队列1 普通队列2  死信交换机 死信队列  消费者
    public static final String busi_exchange = "busi_exchange";
    public static final String busi_queue1 = "busi_queue1";
    public static final String busi_queue2 = "busi_queue2";

    public static final String rt_b_ex_to_q1 = "XA";
    public static final String rt_b_ex_to_q2 = "XB";

    public static final String rt_q1_to_d_ex = "YD";
    public static final String rt_q2_to_d_ex = "YD";

    public static final String rt_d_ex_to_dq = "YD";


    public static final String dead_exchange = "dead_exchange";
    public static final String dead_queue1 = "dead_queue1";
    public static final String consumer = null;


    public static final String busi_custtl_queue = " busi_custtl_queue";
    public static final String rt_b_ex_to_custtl = "rt_b_ex_to_custtl";
    public static final String rt_b_custtl_to_d = "rt_b_custtl_to_d";


}
