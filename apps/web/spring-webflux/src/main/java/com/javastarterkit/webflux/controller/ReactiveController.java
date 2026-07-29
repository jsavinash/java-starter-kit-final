// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reactive")
public class ReactiveController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello from WebFlux!");
    }

    @GetMapping("/numbers")
    public Flux<Integer> numbers() {
        return Flux.range(1, 10);
    }

    @GetMapping("/user/{id}")
    public Mono<ResponseEntity<String>> getUser(@PathVariable String id) {
        return Mono.just(ResponseEntity.ok("User: " + id));
    }

    @PostMapping("/process")
    public Mono<String> process(@RequestBody String data) {
        return Mono.just("Processed: " + data.toUpperCase());
    }
}
