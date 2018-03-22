package com.allardworks.workinator3.consumer.rabbitmq;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rabbit")
@Data
@Component
@Builder
public class RabbitMqOptions {
    private String host = "localhost";
    private int port = 5672;
    private String userName;
    private String password;
    private String exchangeName;
    private String exchangeType;
}
