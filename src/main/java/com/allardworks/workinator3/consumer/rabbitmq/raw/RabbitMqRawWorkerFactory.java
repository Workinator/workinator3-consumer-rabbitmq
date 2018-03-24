package com.allardworks.workinator3.consumer.rabbitmq.raw;

import com.allardworks.workinator3.contracts.Assignment;

public interface RabbitMqRawWorkerFactory {
    RabbitMqRawWorker createWorker(Assignment assignment);
}
