package com.allardworks.workinator3.consumer.rabbitmq.raw.internal;

import com.allardworks.workinator3.consumer.rabbitmq.ChannelPerThreadCache;
import com.allardworks.workinator3.consumer.rabbitmq.PartitionQueue;
import com.allardworks.workinator3.consumer.rabbitmq.QueueResolver;
import com.allardworks.workinator3.consumer.rabbitmq.RabbitMqOptions;
import com.allardworks.workinator3.consumer.rabbitmq.raw.RabbitMqRawWorkerFactory;
import com.allardworks.workinator3.contracts.Assignment;
import com.allardworks.workinator3.contracts.AsyncWorker;
import com.rabbitmq.client.Connection;
import lombok.NonNull;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@Primary
public class WorkinatorRabbitMqWorkerRawFactory implements com.allardworks.workinator3.contracts.AsyncWorkerFactory {
    /**
     * Queue resolver is provide by the application.
     * The resolver instructs workinator on which queue to listen to.
     */
    private final Connection connection;
    private final QueueResolver queueResolver;
    private final RabbitMqOptions options;
    private final ChannelPerThreadCache channelCache;
    private final RabbitMqRawWorkerFactory applicationWorkerFactory;

    public WorkinatorRabbitMqWorkerRawFactory(
            @NonNull final Connection connection, // the rabbit connection
            @NonNull final QueueResolver queueResolver, // provided by the app. determines queue/exchange details for the given partition.
            @NonNull final RabbitMqOptions options, // rabbit configuration options. credentials, host, port, etc.
            @NonNull final ChannelPerThreadCache channelCache, // provides the rabbit channel
            @NonNull RabbitMqRawWorkerFactory applicationWorkerFactory // provides the application's worker
    ) throws IOException, TimeoutException {
        this.connection = connection;
        this.queueResolver = queueResolver;
        this.options = options;
        this.channelCache = channelCache;
        this.applicationWorkerFactory = applicationWorkerFactory;
    }

    /**
     * Sets up the queue.
     *
     * @param partitionQueue
     * @throws IOException
     */
    private void setupQueue(final PartitionQueue partitionQueue) throws IOException, TimeoutException {
        // TODO: cache a list of queues that we know have already been created. only create once.
        // always create the queue.
        val channel = channelCache.getChannel();
        channel.queueDeclare(partitionQueue.getQueueName(), true, false, false, null);

        // if exchange is specified, then create it.
        // bind the queue to it.
        if (partitionQueue.getExchangeName() == null) {
            // exchange name not specified, so can't declare the queue and bind it to the exchange.
            return;
        }

        channel.exchangeDeclare(partitionQueue.getExchangeName(), partitionQueue.getExchangeType(), true);
        channel.queueBind(partitionQueue.getQueueName(), partitionQueue.getExchangeName(), partitionQueue.getRoutingKey());
    }

    @Override
    public AsyncWorker createWorker(final Assignment assignment) {
        val queueDetails = queueResolver.getQueueForPartition(assignment);
        try {
            setupQueue(queueDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create the app worker.
        // wrap it with the workinator rabbitmq worker.
        val channel = channelCache.getChannel();
        val worker = applicationWorkerFactory.createWorker(assignment);
        return new WorkinatorRabbitMqRawWorker(channel, worker);
    }
}
