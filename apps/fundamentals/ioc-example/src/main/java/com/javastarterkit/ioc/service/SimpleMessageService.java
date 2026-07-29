// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ioc.service;

import com.javastarterkit.ioc.model.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Simple implementation of MessageService.
 * Demonstrates @Service annotation and dependency injection.
 */
@Service
@Scope("singleton")
public class SimpleMessageService implements MessageService {

    private final String processorName;

    public SimpleMessageService() {
        this.processorName = "DefaultProcessor";
    }

    @Override
    public String processMessage(Message message) {
        return String.format(
                "[%s] Processing message from '%s': %s", processorName, message.getSender(), message.getContent());
    }
}
