package com.burny.rabbitmq.two_work_queues;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/17 23:46
 */

@Slf4j
public class Producer {

    //队列名称
    public static final String queue_name = "hello";

    public static final String content = "hello world";

    public static void main(String[] args) throws Exception {
        Channel channel = Info.getC();
        //exclusive:消息是否被共享
        channel.queueDeclare(queue_name, true, true, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            for (int i = 0; i < 100; i++) {
                channel.basicPublish("", queue_name, null, (next + i).getBytes());
            }
        }

    }

}
