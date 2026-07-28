package com.javastarterkit.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RedisApplicationTests {

    @Test
    void contextLoads() {
        // Verify Redis context loads successfully
    }
}