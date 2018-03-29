package com.allardworks.workinator3.consumer.rabbitmq.raw.internal;

import com.allardworks.workinator3.consumer.AsyncWorker;
import com.allardworks.workinator3.consumer.WorkerContext;
import com.allardworks.workinator3.consumer.rabbitmq.raw.RabbitMqRawWorker;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * This is the worker used by workinator.
 * This worker proxies down to RabbitMqRawWorker, which is provided by the application
 * via RabbitMqWorkerFactory.
 */
@RequiredArgsConstructor
public class WorkinatorRabbitMqRawWorker implements AsyncWorker {
    private final Channel channel;
    private final RabbitMqRawWorker worker;

    @Override
    public void execute(@NotNull final WorkerContext context) {
        do {
            worker.processMessage(context, channel);
        } while (context.hasWork());
    }

    @Override
    public void close() throws Exception {
    }
}
