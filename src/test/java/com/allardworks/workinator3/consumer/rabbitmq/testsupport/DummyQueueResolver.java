package com.allardworks.workinator3.consumer.rabbitmq.testsupport;

import com.allardworks.workinator3.consumer.rabbitmq.PartitionQueue;
import com.allardworks.workinator3.consumer.rabbitmq.QueueResolver;
import com.allardworks.workinator3.contracts.Assignment;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class DummyQueueResolver implements QueueResolver {
    @Override
    public PartitionQueue getQueueForPartition(Assignment assignment) {
        return
                PartitionQueue
                .builder()
                .queueName("Partition-" + assignment.getPartitionKey())
                .routingKey("*")
                .build();
    }
}
