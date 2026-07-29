// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow messageFlow() {
        return IntegrationFlows.from("inputChannel")
                .<String, String>transform(String::toUpperCase)
                .handle((GenericHandler<String>) (payload, headers) -> {
                    System.out.println("Processing: " + payload);
                    return payload + " - PROCESSED";
                })
                .channel("outputChannel")
                .get();
    }
}
