package com.allardworks.workinator3.consumer.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.NonNull;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Connection getConnection(@NonNull final RabbitMqOptions options) throws IOException, TimeoutException {
        val factory = new ConnectionFactory();
        factory.setHost(options.getHost());
        factory.setPort(options.getPort());
        factory.setUsername(options.getUserName());
        factory.setPassword(options.getPassword());
        return factory.newConnection();
    }
}
