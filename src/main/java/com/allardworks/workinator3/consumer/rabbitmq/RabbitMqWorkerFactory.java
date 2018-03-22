package com.allardworks.workinator3.consumer.rabbitmq;

import com.allardworks.workinator3.contracts.Assignment;
import com.allardworks.workinator3.contracts.AsyncWorker;
import com.allardworks.workinator3.contracts.WorkerContext;
import com.rabbitmq.client.Connection;
import lombok.NonNull;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@Primary
public class RabbitMqWorkerFactory implements com.allardworks.workinator3.contracts.AsyncWorkerFactory {
    /**
     * Queue resolver is provide by the application.
     * The resolver instructs workinator on which queue to listen to.
     */
    private final Connection connection;
    private final QueueResolver queueResolver;
    private final RabbitMqOptions options;
    private final ChannelCache channelCache;

    public RabbitMqWorkerFactory(@NonNull final Connection connection, @NonNull final QueueResolver queueResolver, @NonNull final RabbitMqOptions options, @NonNull final ChannelCache channelCache) throws IOException, TimeoutException {
        this.connection = connection;
        this.queueResolver = queueResolver;
        this.options = options;
        this.channelCache = channelCache;
        setupExchange();
    }

    /**
     * Declare the exchange.
     * Occurs once.
     * @throws IOException
     * @throws TimeoutException
     */
    private void setupExchange() throws IOException, TimeoutException {
        if (options.getExchangeName() == null) {
            return;
        }

        try (val channel = connection.createChannel()) {
            channel.exchangeDeclare(options.getExchangeName(), options.getExchangeType(), true);
        }
    }

    /**
     * Sets up the queue.
     * @param partitionQueue
     * @throws IOException
     */
    private void setupQueue(final PartitionQueue partitionQueue) throws IOException {
        // TODO: cache a list of queues that we know have already been created. only create once.
        if (options.getExchangeName() == null) {
            // exchange name not specified, so can't declare the queue and bind it to the exchange.
            return;
        }

        val channel = channelCache.getChannel();
        channel.queueDeclare(partitionQueue.getQueueName(), true, false, false, null);
        channel.queueBind(partitionQueue.getQueueName(), options.getExchangeName(), partitionQueue.getRoutingKey());
    }

    @Override
    public AsyncWorker createWorker(final Assignment assignment) {
        val queueDetails = queueResolver.getQueueForPartition(assignment);
        try {
            setupQueue(queueDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
