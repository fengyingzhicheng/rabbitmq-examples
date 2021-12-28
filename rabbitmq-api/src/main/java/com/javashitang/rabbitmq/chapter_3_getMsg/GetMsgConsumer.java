package com.javashitang.rabbitmq.chapter_3_getMsg;

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
public class GetMsgConsumer {

    public static void main(String[] args)
        throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = ConnectionUtil.getConnectionFactory();

        Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(GetMsgProducer.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = "errorQueue";
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, GetMsgProducer.EXCHANGE_NAME, "error");

        while(true) {
            GetResponse getResponse = channel.basicGet(queueName, true);
            if (null != getResponse) {
                log.info("get message, routingKey: {}, message: {}", getResponse.getEnvelope().getRoutingKey(), new String(getResponse.getBody()));
            }
            Thread.sleep(1000);
        }
    }
}
