package com.burny.rabbitmq.one;

import com.rabbitmq.client.*;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/18 0:13
 */

public class Consumer {

    public static final String ip = "192.168.1.176";
    public static final String port = "192.168.1.176";
    public static final String username = "root";
    public static final String password = "root";
    //队列名称
    public static final String queue_name = "hello";

    public static final String content = "hello world";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ip);
        factory.setUsername(username);
        factory.setPassword(password);

        //创建连接
        Connection connection = factory.newConnection();

        //获取信道
        Channel channel = connection.createChannel();

        //声明:
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("成功接收消息");
            System.out.println(message.getBody());
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消费被中断");
        };
        //基础消费
        /**
         *绑定队列名称
         * 自动应答
         * 消费失败的回调
         * 消费成功的回调
         */
        channel.basicConsume(queue_name, true, deliverCallback, cancelCallback);

    }
}
