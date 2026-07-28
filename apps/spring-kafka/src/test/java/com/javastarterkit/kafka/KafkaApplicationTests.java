package com.javastarterkit.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class KafkaApplicationTests {

    @Test
    void contextLoads() {
        // Verify Kafka context loads successfully
    }
}