package com.burny.rabbitmq.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/18 22:55
 */

public class Info {
    public static final String ip = "192.168.1.176";
    public static final String username = "root";
    public static final String password = "root";

    public static final String queue_name = "hello";

    public static final String pre = "接收到消息: ";

    public static final String callback = "消费者取消消费的回调";

    @SneakyThrows
    public static Channel getC() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ip);
        factory.setUsername(username);
        factory.setPassword(password);
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        return channel;
    }


}
