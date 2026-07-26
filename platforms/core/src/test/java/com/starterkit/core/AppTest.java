package com.starterkit.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit test for App.
 */
class AppTest {

    @Test
    void testGreet() {
        App app = new App();
        assertEquals("Hello, World!", app.greet("World"));
    }
}
