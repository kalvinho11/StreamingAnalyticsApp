package com.example.streaminganalytics.consumer;

import com.example.streaminganalytics.Constants;
import com.example.streaminganalytics.domain.DataInput;
import com.example.streaminganalytics.service.StatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The message consumer.
 */
@RabbitListener(queues = Constants.QUEUE_NAME)
@Component
public class Consumer {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Consumes messages from the RabbitMQ.
     *
     * @param input the message of the queue.
     * @throws IOException
     */
    @RabbitHandler
    public void consume(byte[] input) throws IOException {
        final String message = new String(input, StandardCharsets.UTF_8);
        final DataInput dataInput = new ObjectMapper().readValue(message, DataInput.class);
        System.out.println("Message recibed: " + dataInput);
        this.statisticsService.doCalculations(dataInput);
    }
}
