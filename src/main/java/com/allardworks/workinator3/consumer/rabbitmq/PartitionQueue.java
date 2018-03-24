package com.allardworks.workinator3.consumer.rabbitmq;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PartitionQueue {
    /**
     * The name of the queue for the for the partition.
     */
    private final String queueName;

    /**
     * Used when creating the queue and binding it to the exchange.
     */
    private String routingKey;

    private String exchangeName;

    private String exchangeType;
}
