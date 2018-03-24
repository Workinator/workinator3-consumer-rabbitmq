package com.allardworks.workinator3.consumer.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
public class ChannelPerThreadCache implements AutoCloseable {
    private final Connection connection;
    private final Map<Thread, Channel> channels = new ConcurrentHashMap<>();

    public Channel getChannel() {
        return channels.computeIfAbsent(Thread.currentThread(), t -> {
            try {
                return connection.createChannel();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public void close() throws Exception {
        for (val channel : channels.values()) {
            try {
                channel.close();
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
