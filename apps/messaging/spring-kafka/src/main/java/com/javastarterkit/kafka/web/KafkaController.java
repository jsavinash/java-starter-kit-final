// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.kafka.web;

import com.javastarterkit.kafka.entity.MessageEvent;
import com.javastarterkit.kafka.service.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageEvent event) {
        kafkaProducer.sendMessage(event);
        return ResponseEntity.ok("Message sent to Kafka");
    }
}
