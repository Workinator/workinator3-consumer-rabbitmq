package com.allardworks.workinator3.consumer.rabbitmq;

import com.allardworks.workinator3.core.Assignment;

public interface QueueResolver {
    PartitionQueue getQueueForPartition(Assignment assignment);
}
