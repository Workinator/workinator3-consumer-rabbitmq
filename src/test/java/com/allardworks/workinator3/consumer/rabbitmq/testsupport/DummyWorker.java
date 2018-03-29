package com.allardworks.workinator3.consumer.rabbitmq.testsupport;

import com.allardworks.workinator3.consumer.WorkerContext;
import com.allardworks.workinator3.consumer.rabbitmq.raw.RabbitMqRawWorker;
import com.rabbitmq.client.Channel;

import static java.lang.System.out;

public class DummyWorker implements RabbitMqRawWorker {
    @Override
    public void processMessage(WorkerContext context, Channel channel) {
        out.println("demo worker");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
