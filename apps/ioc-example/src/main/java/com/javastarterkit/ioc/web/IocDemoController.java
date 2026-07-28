// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ioc.web;

import com.javastarterkit.ioc.model.Message;
import com.javastarterkit.ioc.service.MessageService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller demonstrating IoC Container usage.
 */
@RestController
@RequestMapping("/api/ioc-demo")
public class IocDemoController {

    private final MessageService messageService;
    private final ObjectProvider<Message> prototypeMessageProvider;
    private final Message defaultMessage;

    /**
     * Constructor injection - demonstrates Spring's dependency injection.
     */
    public IocDemoController(
            MessageService messageService, ObjectProvider<Message> prototypeMessageProvider, Message defaultMessage) {
        this.messageService = messageService;
        this.prototypeMessageProvider = prototypeMessageProvider;
        // Defensive copy to avoid exposing internal representation
        this.defaultMessage = new Message(defaultMessage.getContent(), defaultMessage.getSender());
    }

    @GetMapping("/process/default")
    public ResponseEntity<Map<String, String>> processDefaultMessage() {
        Map<String, String> response = new HashMap<>();
        response.put("message", messageService.processMessage(defaultMessage));
        response.put("beanScope", "singleton");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processCustomMessage(@RequestBody Message message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", messageService.processMessage(message));
        response.put("beanScope", "singleton");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/process/prototype")
    public ResponseEntity<Map<String, String>> processPrototypeMessage() {
        Message prototypeMessage = prototypeMessageProvider.getObject();

        Map<String, String> response = new HashMap<>();
        response.put("message", messageService.processMessage(prototypeMessage));
        response.put("beanScope", "prototype");
        response.put("messageId", prototypeMessage.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("application", "Spring IoC Container Example");
        info.put("features", "Constructor Injection, @Bean Methods, Bean Scopes");
        info.put("endpoints", "/api/ioc-demo/process/default, /api/ioc-demo/process, /api/ioc-demo/process/prototype");
        return ResponseEntity.ok(info);
    }
}
