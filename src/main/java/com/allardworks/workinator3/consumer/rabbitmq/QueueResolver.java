package com.allardworks.workinator3.consumer.rabbitmq;

import com.allardworks.workinator3.contracts.Assignment;
import com.allardworks.workinator3.httpapi.Partition;

public interface QueueResolver {
    PartitionQueue getQueueForPartition(Assignment assignment);
}
