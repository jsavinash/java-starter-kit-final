package com.javastarterkit.integration.web;

import org.springframework.http.ResponseEntity;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/integration")
public class IntegrationController {

    private final DirectChannel inputChannel;

    public IntegrationController(DirectChannel inputChannel) {
        this.inputChannel = inputChannel;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processMessage(@RequestBody String message) {
        Message<String> msg = MessageBuilder.withPayload(message)
                .setHeader("timestamp", System.currentTimeMillis())
                .build();
        
        inputChannel.send(msg);
        return ResponseEntity.ok("Message sent to integration flow");
    }
}