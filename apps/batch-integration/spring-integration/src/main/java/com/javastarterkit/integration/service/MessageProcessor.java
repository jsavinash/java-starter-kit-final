// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.integration.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor {

    @ServiceActivator(inputChannel = "outputChannel")
    public String processMessage(Message<String> message) {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
        return payload + " [FINAL]";
    }
}
