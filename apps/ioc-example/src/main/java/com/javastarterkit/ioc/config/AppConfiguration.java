// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ioc.config;

import com.javastarterkit.ioc.model.Message;
import com.javastarterkit.ioc.service.MessageService;
import com.javastarterkit.ioc.service.SimpleMessageService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * Configuration class demonstrating @Bean methods and dependency injection.
 * Shows how to configure beans using Java-based configuration.
 */
@Configuration
@ComponentScan(basePackages = "com.javastarterkit.ioc")
public class AppConfiguration {

    /**
     * Creates a Message bean using @Bean annotation.
     * Demonstrates method-based bean definition in configuration class.
     */
    @Bean
    public Message defaultMessage() {
        return new Message("Hello from IoC Container!", "System");
    }

    /**
     * Creates a MessageService bean with constructor injection.
     * Demonstrates programmatic bean creation with dependencies.
     */
    @Bean
    @Primary
    public MessageService enhancedMessageService(Message defaultMessage) {
        return new SimpleMessageService() {
            @Override
            public String processMessage(Message message) {
                if (message == null) {
                    message = defaultMessage;
                }
                return "[EnhancedService] " + super.processMessage(message);
            }
        };
    }

    /**
     * Prototype-scoped Message bean.
     * A new instance is created each time the bean is requested.
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Message prototypeMessage() {
        return new Message("Prototype message at " + System.currentTimeMillis(), "PrototypeFactory");
    }
}
