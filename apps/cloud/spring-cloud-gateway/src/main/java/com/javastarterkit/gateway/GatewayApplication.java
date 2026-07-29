// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/users/**").uri("http://localhost:8081"))
                .route("product-service", r -> r.path("/api/products/**").uri("http://localhost:8082"))
                .route("order-service", r -> r.path("/api/orders/**").uri("http://localhost:8083"))
                .build();
    }
}
