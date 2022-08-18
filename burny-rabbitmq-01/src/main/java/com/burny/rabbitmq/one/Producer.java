package com.burny.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/17 23:46
 */

@Slf4j
public class Producer {

    public static final String ip = "192.168.1.176";
    public static final String port = "192.168.1.176";
    public static final String username = "root";
    public static final String password = "root";
    //队列名称
    public static final String queue_name = "hello";

    public static final String content = "hello world";

    public static void main(String[] args) throws Exception {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ip);
        factory.setUsername(username);
        factory.setPassword(password);

        //创建连接
        Connection connection = factory.newConnection();

        //获取信道
        Channel channel = connection.createChannel();

        //实现信道绑定队列
        /**
         * String queue, boolean durable, boolean exclusive, boolean autoDelete,
         *                                  Map<String, Object> arguments
         *队列名称
         * 是否持久化, 默认不持久化,指的是队列持久化:
         * 该队列是否只供一个消费者消费，即排他，是否进行消息共享,一个消息只能被一个消费者消费
         * 是否自动删除
         * 最后一个消费者端开链接猴,该队列是否自动删除
         *
         */
        channel.queueDeclare(queue_name, true, false, false, null);


        //发送消息体
        /**
         *发送到哪个交换机,""为默认交换机
         * 路由的key值是哪个,本次是队列的名称
         * 其他参数
         * 发送消息的消息体
         */
        channel.basicPublish("", queue_name, null, content.getBytes());

        //在web界面找到 QUEUE 中的hello队列: ready :正在准备被消费者消费,总共有一个消息,
        //重启发现队列还在,但是消息不见了.


    }

}
