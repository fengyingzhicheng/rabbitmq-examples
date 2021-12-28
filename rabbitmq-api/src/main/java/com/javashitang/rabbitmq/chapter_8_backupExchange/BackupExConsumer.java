package com.javashitang.rabbitmq.chapter_8_backupExchange;

import com.javashitang.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: lilimin
 * @Date: 2019/8/26 23:30
 */
@Slf4j
public class BackupExConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = ConnectionUtil.getConnectionFactory();

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(BackupExProducer.BAK_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String queueName = "notErrorQueue";
        channel.queueDeclare(queueName, false, false, false, null);

        // fanout类型的交换器bindingKey不起作用，这里随便写了一个#
        channel.queueBind(queueName, BackupExProducer.BAK_EXCHANGE_NAME, "#");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                log.info("get message, routingKey: {}, message: {}", envelope.getRoutingKey(), message);
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
