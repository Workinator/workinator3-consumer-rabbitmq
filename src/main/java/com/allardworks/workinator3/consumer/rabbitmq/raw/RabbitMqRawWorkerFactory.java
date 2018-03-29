package com.allardworks.workinator3.consumer.rabbitmq.raw;

import com.allardworks.workinator3.core.Assignment;

public interface RabbitMqRawWorkerFactory {
    RabbitMqRawWorker createWorker(Assignment assignment);
}
