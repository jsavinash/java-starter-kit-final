// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.amqp.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
