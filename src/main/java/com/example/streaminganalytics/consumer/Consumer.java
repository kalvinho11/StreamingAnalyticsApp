package com.example.streaminganalytics.consumer;

import com.example.streaminganalytics.Constants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The message consumer.
 */
@RabbitListener(queues = Constants.QUEUE_NAME)
@Component
public class Consumer {

    /**
     * Consumes messages from the RabbitMQ.
     *
     * @param input the message of the queue.
     * @throws IOException
     */
    @RabbitHandler
    public void consume(byte[] input) throws IOException {
        String message = new String(input, StandardCharsets.UTF_8);
        System.out.println("Message recibed: " + message);
    }
}
