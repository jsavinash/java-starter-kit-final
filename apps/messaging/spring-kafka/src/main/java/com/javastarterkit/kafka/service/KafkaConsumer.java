package com.javastarterkit.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.javastarterkit.kafka.entity.MessageEvent;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void consume(MessageEvent event) {
        System.out.println("Received message: " + event.getContent());
    }
}