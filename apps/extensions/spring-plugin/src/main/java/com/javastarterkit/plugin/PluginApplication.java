// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class PluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(PluginApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/plugins")
class PluginController {

    @GetMapping
    public String listPlugins() {
        return "Plugin registry - no plugins registered";
    }
}
