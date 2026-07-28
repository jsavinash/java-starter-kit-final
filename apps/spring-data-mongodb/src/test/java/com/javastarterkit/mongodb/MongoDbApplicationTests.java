package com.javastarterkit.mongodb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MongoDbApplicationTests {

    @Test
    void contextLoads() {
        // Verify MongoDB context loads successfully
    }
}