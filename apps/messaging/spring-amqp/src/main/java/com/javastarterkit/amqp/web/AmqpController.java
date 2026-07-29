// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.amqp.web;

import com.javastarterkit.amqp.service.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/amqp")
public class AmqpController {

    private final MessageProducer messageProducer;

    public AmqpController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        messageProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to queue");
    }
}
