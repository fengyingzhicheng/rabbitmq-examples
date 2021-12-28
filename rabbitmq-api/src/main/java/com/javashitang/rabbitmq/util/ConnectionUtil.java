package com.javashitang.rabbitmq.util;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author 王军
 * @description
 * @date 2021/12/28 17:24
 */
public class ConnectionUtil {

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.77.219.136");
        connectionFactory.setPort(5672);
        connectionFactory.setPassword("123");
        connectionFactory.setUsername("admin");
        return connectionFactory;
    }
}