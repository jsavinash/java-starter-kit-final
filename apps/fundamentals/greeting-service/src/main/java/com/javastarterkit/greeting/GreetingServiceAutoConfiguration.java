// Copyright © 2012-2024 Java Starter Kit. All rights reserved.
package com.javastarterkit.greeting;

import com.javastarterkit.greeting.config.GreetingProperties;
import com.javastarterkit.greeting.service.DefaultGreetingService;
import com.javastarterkit.greeting.service.GreetingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for the greeting service.
 *
 * @author Spring Boot Engineering
 * @since 3.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GreetingProperties.class)
public class GreetingServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GreetingService greetingService(GreetingProperties properties) {
        return new DefaultGreetingService(properties);
    }
}
