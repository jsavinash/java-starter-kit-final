package com.starterkit.core;

import java.util.logging.Logger;

/**
 * Core application entry point.
 * Compiled with Java 25 toolchain.
 */
public class App {

    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOG.info("Java Starter Kit Core running on Java " + Runtime.version());
    }

    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}
