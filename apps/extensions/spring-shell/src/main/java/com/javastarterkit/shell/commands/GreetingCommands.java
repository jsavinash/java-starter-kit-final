// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.shell.commands;

import org.springframework.shell.command.annotation.Command;
import org.springframework.stereotype.Component;

@Component
public class GreetingCommands {

    @Command(command = "hello", alias = "hi", description = "Say hello")
    public String hello(
            @Command.Option(
                            names = {"--name", "-n"},
                            description = "Name to greet")
                    String name) {
        return "Hello, " + (name != null ? name : "World") + "!";
    }

    @Command(command = "greet all", description = "Greet everyone")
    public String greetAll() {
        return "Greetings to all!";
    }
}
