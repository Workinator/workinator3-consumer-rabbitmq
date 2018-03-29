package com.allardworks.workinator3.consumer.rabbitmq.raw;

import com.allardworks.workinator3.consumer.WorkerContext;
import com.rabbitmq.client.Channel;

public interface RabbitMqRawWorker {
    void processMessage(WorkerContext context, Channel channel);
}
