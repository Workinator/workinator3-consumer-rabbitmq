package com.allardworks.workinator3.consumer.rabbitmq;

import com.allardworks.workinator3.contracts.AsyncWorker;
import com.allardworks.workinator3.contracts.WorkerContext;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class RabbitMqWorker implements AsyncWorker {
    private final Channel channel;

    @Override
    public void execute(@NotNull final WorkerContext context) {

    }

    @Override
    public void close() throws Exception {

    }
}
