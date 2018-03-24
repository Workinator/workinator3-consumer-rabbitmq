package com.allardworks.workinator3.consumer.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rabbitmq")
@Data
@Component
public class RabbitMqOptions {
    private String host = "localhost";
    private int port = 5672;
    private String userName;
    private String password;
}
