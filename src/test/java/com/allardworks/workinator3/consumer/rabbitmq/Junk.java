package com.allardworks.workinator3.consumer.rabbitmq;

import com.allardworks.workinator3.consumer.rabbitmq.raw.internal.WorkinatorRabbitMqWorkerRawFactory;
import com.allardworks.workinator3.consumer.rabbitmq.testsupport.DummyQueueResolver;
import com.allardworks.workinator3.core.Assignment;
import com.allardworks.workinator3.core.ConsumerId;
import com.allardworks.workinator3.core.ConsumerRegistration;
import com.allardworks.workinator3.core.WorkerId;
import com.rabbitmq.client.ConnectionFactory;
import lombok.val;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

public class Junk {
    @Test
    public void boo() throws IOException, TimeoutException {
        val factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");
        factory.setPort(5672);

        val connection = factory.newConnection();
        val resolver = new DummyQueueResolver();
        val options = new RabbitMqOptions();

        val channelCache = new ChannelPerThreadCache(connection);
        val workinatorFactory = new WorkinatorRabbitMqWorkerRawFactory(connection, resolver, options, channelCache, null);

        val consumerId = new ConsumerId("boo");
        val registration = new ConsumerRegistration(consumerId, "receipt");
        val workerId = new WorkerId(registration, 0);
        val assignment = new Assignment(workerId, "zzz", "", "", new Date());


        val worker = workinatorFactory.createWorker(assignment);
        assertNotNull(worker);
    }
}
