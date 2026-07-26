package com.starterkit.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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