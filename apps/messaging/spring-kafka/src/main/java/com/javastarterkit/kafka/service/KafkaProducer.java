// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.kafka.service;

import com.javastarterkit.kafka.entity.MessageEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "demo-topic";

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
