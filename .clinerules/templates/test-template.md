# Test Class Template

## Test Class Structure

```java
package com.javastarterkit.patterns.[pattern];

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[Pattern Name] Tests")
class [PatternName]Test {

    private [ComponentUnderTest] component;

    @BeforeEach
    void setUp() {
        // Initialize test fixtures
    }

    @Test
    @DisplayName("Should [expected behavior] when [scenario]")
    void should_expectedBehavior_when_scenario() {
        // Arrange
        // Act
        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception when [error scenario]")
    void should_throwException_when_errorScenario() {
        // Arrange
        // Act & Assert
        assertThatThrownBy(() -> component.method(input))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Expected error message");
    }
}
```

## Concurrency Test Template

```java
package com.javastarterkit.patterns.[pattern];

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Pattern Name] Concurrency Tests")
class [PatternName]ConcurrencyTest {

    @Test
    @DisplayName("Should handle concurrent [operations] safely")
    void should_handleConcurrentOperationsSafely() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // Perform concurrent operations
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // Assert thread-safe behavior
        assertThat(result).isEqualTo(expected);
    }
}
```

## Testing Best Practices

### Naming Convention
- Test methods: `should_expectedBehavior_when_scenario()`
- Test classes: `<ClassName>Test` or `<ClassName>ConcurrencyTest`
- Always use `@DisplayName` for readable test descriptions

### Test Structure (AAA Pattern)
1. **Arrange**: Set up test data and preconditions
2. **Act**: Execute the method under test
3. **Assert**: Verify the results

### Coverage Requirements
- Target 80%+ line coverage (JaCoCo)
- Test happy paths and edge cases
- Include concurrency tests for thread-safe components
- Test error conditions and exception handling

### Assertions
- Use AssertJ for fluent assertions
- Prefer specific assertions over generic ones
- Test both positive and negative cases

### Mocking
- Use Mockito for mocking dependencies
- Constructor injection only (no field injection in tests)
- Verify mock interactions when relevant