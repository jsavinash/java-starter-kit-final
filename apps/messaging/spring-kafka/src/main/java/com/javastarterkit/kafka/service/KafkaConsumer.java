// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.kafka.service;

import com.javastarterkit.kafka.entity.MessageEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void consume(MessageEvent event) {
        System.out.println("Received message: " + event.getContent());
    }
}
