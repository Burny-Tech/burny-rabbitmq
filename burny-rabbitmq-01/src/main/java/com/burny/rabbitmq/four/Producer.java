package com.burny.rabbitmq.four;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.MessageProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Note TODO
 * @Author cyx
 * @Date 2022/8/21 0:17
 */
@Slf4j
public class Producer {
    private static final Integer batch = 100;

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = Info.getC();
        channel.confirmSelect();
        channel.queueDeclare(Info.queue_name, true, false, false, null);
        Scanner scanner = new Scanner(System.in);

        String next = scanner.next();
        //1. 单个确认发布
        channel.basicPublish("", Info.queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, (next).getBytes("UTF-8"));
        channel.waitForConfirms();

        //2 .批量确认
        channel.basicPublish("", Info.queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, (next).getBytes("UTF-8"));
        for (int i = 0; i < 2000; i++) {
            if (i % batch == 0) {
                channel.waitForConfirms();
            }

            //3.异步确认

            ConfirmListener confirmListener = new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    //消息成功时处理
                    //参数说明:消息的标记,是否批量确认
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    //消息拒绝时处理
                    //参数说明:消息的标记,是否批量确认

                }
            };
            channel.addConfirmListener(confirmListener);
            channel.basicPublish("", Info.queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, (next).getBytes("UTF-8"));

            //4.解决未确认的消息
            //线程安全有序的哈希表,适用高并发的情况下
            /**
             *轻松的将序号与消息进行关联,
             * 轻松的批量删除内容
             * 支持高并发,多线程
             */
            ConcurrentSkipListMap<Long, String> outStandingConfirm = new ConcurrentSkipListMap<>();
            String content = "消息内容";
            outStandingConfirm.put(channel.getNextPublishSeqNo(), content);
            ConfirmListener confirmListener1 = new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    //消息成功时处理
                    //参数说明:消息的标记,是否批量确认
                    outStandingConfirm.headMap(deliveryTag);
                    if (multiple) {
                        outStandingConfirm.headMap(deliveryTag);
                        outStandingConfirm.clear();
                    } else {
                        outStandingConfirm.remove(deliveryTag);
                    }
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    //消息拒绝时处理
                    //参数说明:消息的标记,是否批量确认
                    outStandingConfirm.headMap(deliveryTag);


                }
            };

            //穿建临时队列
            channel.queueDeclare().getQueue();
        }


    }


}
