package com.allardworks.workinator3.consumer.rabbitmq.testsupport;

import com.allardworks.workinator3.consumer.rabbitmq.raw.RabbitMqRawWorker;
import com.allardworks.workinator3.consumer.rabbitmq.raw.RabbitMqRawWorkerFactory;
import com.allardworks.workinator3.contracts.Assignment;
import org.springframework.stereotype.Component;

@Component
public class DummyFactory implements RabbitMqRawWorkerFactory {
    @Override
    public RabbitMqRawWorker createWorker(Assignment assignment) {
        return new DummyWorker();
    }
}
