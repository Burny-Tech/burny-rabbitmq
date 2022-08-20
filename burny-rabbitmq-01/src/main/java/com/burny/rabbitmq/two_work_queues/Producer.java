package com.burny.rabbitmq.two_work_queues;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/17 23:46
 */

@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception {
        Channel channel = Info.getC();
        //exclusive:消息是否被共享
        channel.queueDeclare(Info.queue_name, true, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            //for (int i = 0; i < 1000000; i++) {
            //    log.info("发送一次"+i);
            //消息持久化
            channel.basicPublish("", Info.queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, (next).getBytes("UTF-8"));

            //channel.basicPublish("", Info.queue_name, null, (next).getBytes("UTF-8"));
            //}
        }

    }

}
