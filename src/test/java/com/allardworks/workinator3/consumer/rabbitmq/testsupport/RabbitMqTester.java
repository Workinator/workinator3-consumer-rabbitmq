package com.allardworks.workinator3.consumer.rabbitmq.testsupport;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.rabbitmq.client.BuiltinExchangeType.TOPIC;

public class RabbitMqTester implements AutoCloseable {
    private final List<String> exchanges = new ArrayList<>();
    private final List<String> queues = new ArrayList<>();
    private final Channel channel;

    public RabbitMqTester(@NonNull final Connection connection) throws IOException {
        channel = connection.createChannel();
    }

    public RabbitMqTester declareTopic(@NonNull final String exchangeName) throws IOException {
        channel.exchangeDeclare(exchangeName, TOPIC, true, true, null);
        return this;
    }

    public RabbitMqTester declareQueue(@NonNull final String queueName, @NonNull final String exchangeToBindTo, @NonNull final String routingKey) throws IOException {
        channel.queueDeclare(queueName, true, false,  false, null);
        channel.queueBind(queueName, exchangeToBindTo, routingKey);
        return this;
    }

    public RabbitMqTester publish(@NonNull final String exchangeName, @NonNull final String routingKey, @NonNull final String message) throws IOException {
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        return this;
    }

    private void deleteQueues() throws IOException {
        for (val queue : queues) {
            channel.queueDelete(queue);
        }
    }

    private void deleteExchanges() throws IOException {
        for (val exchange : exchanges) {
            channel.exchangeDelete(exchange, false);
        }
    }


    @Override
    public void close() throws Exception {
        deleteQueues();
        deleteExchanges();
    }
}
