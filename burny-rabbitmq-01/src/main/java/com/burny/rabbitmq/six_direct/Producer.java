package com.burny.rabbitmq.six_direct;

import com.burny.rabbitmq.common.Info;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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
        //如果发送前需要确保消费者已经处于监听状态,否则消息会丢失
        Channel channel = Info.getC();
        //channel.exchangeDeclare(Info.exchange_name, BuiltinExchangeType.FANOUT);
        channel.confirmSelect();
        channel.exchangeDeclare(Info.exchange_name, BuiltinExchangeType.DIRECT, true, false, null);
        //internal 参数:如果为true 则无法发第二遍
        Scanner scanner = new Scanner(System.in);


        ConfirmListener confirmListener1 = new ConfirmListener() {
            Integer a = 0;

            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                log.info("消息已别接收,deliveryTag:{},监听器收到的总次数:{}", deliveryTag, a);
                a++;
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息已经被接拒收");


            }
        };
        channel.addConfirmListener(confirmListener1);

        while (scanner.hasNext()) {
            byte[] bytes = scanner.next().getBytes(StandardCharsets.UTF_8);
            String s = new String(bytes);
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    channel.basicPublish(Info.exchange_name, "info", null, (s + "\t" + i + "\t" + "info").getBytes(StandardCharsets.UTF_8));
                    System.out.println("info:" + "发送次数" + i + ";发送内容info:" + (new String(bytes) + "info") + ";下一次发送的deliveryTag" + channel.getNextPublishSeqNo());
                }
                if (i % 2 == 1) {
                    channel.basicPublish(Info.exchange_name, "warn", null, (s + "\t" + i + "\t" + "warn").getBytes(StandardCharsets.UTF_8));
                    System.out.println("warn:" + "发送次数" + i + ";发送内容:warn" + (new String(bytes) + "warn") + ";下一次发送的deliveryTag" + channel.getNextPublishSeqNo());
                }
                if (i % 5 == 0) {
                    channel.basicPublish(Info.exchange_name, "error", null, (s + "\t" + i + "\t" + "error").getBytes(StandardCharsets.UTF_8));
                    System.out.println("error:" + "发送次数" + i + ";发送内容:error" + (new String(bytes) + "error") + ";下一次发送的deliveryTag" + channel.getNextPublishSeqNo());
                }
            }
        }


    }


}
