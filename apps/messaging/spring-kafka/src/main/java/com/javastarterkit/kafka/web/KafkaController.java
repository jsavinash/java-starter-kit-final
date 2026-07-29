package com.javastarterkit.kafka.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javastarterkit.kafka.entity.MessageEvent;
import com.javastarterkit.kafka.service.KafkaProducer;

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