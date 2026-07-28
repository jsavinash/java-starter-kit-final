# Spring Boot Study Material

This directory contains comprehensive study material demonstrating Spring Boot patterns, auto-configuration, and best practices.

## 📚 Learning Modules

### 1. Custom Auto-Configuration Library (`greeting-service`)

A complete example of a Spring Boot starter library following Spring Boot's auto-configuration pattern.

#### Key Concepts Demonstrated

**1.1 Service Abstraction Layer**
- Interface: `GreetingService` - defines the contract
- Implementation: `DefaultGreetingService` - provides the default behavior
- Demonstrates proper interface-segregation and dependency injection

**1.2 Configuration Properties**
- `GreetingProperties` - type-safe configuration using `@ConfigurationProperties`
- Shows how to bind application.yml properties to Java objects
- Prefix-based configuration: `app.greeting.*`

**1.3 Auto-Configuration Pattern**
- `GreetingServiceAutoConfiguration` - the heart of the starter
- Uses `@ConditionalOnMissingBean` for extensibility
- Uses `@EnableConfigurationProperties` to activate configuration
- Registered in `META-INF/spring.factories`

**1.4 Spring Boot Conventions**
- Apache 2.0 license headers on all files
- Javadoc comments on all public APIs
- Package-private visibility by default
- Constructor injection for immutability

#### Project Structure
```
greeting-service/
├── build.gradle.kts                                    # Library build configuration
├── src/main/java/com/javastarterkit/greeting/
│   ├── GreetingServiceAutoConfiguration.java          # Auto-config entry point
│   ├── config/
│   │   └── GreetingProperties.java                    # Configuration properties
│   └── service/
│       ├── GreetingService.java                       # Service interface
│       └── DefaultGreetingService.java                # Default implementation
└── src/main/resources/META-INF/
    └── spring.factories                               # Auto-config registration
```

#### Usage Example

```java
// In any Spring Boot application
@Autowired
private GreetingService greetingService;

// Use the service
String message = greetingService.greet("Spring Developer");
// Output: "Hello, Spring Developer!" (or configured prefix/suffix)
```

**application.yml configuration:**
```yaml
app:
  greeting:
    prefix: "Hi"           # Custom greeting prefix
    suffix: "!!!"          # Custom suffix
    include-time: true     # Include current time in greeting
```

---

### 2. Basic Web Application (`hello-world-basics`)

A simple Spring Boot web application demonstrating core concepts.

#### Key Concepts Demonstrated

**2.1 Spring Boot Application**
- `@SpringBootApplication` - enables auto-configuration, component scanning, and configuration
- Main class with `SpringApplication.run()`

**2.2 REST Controller**
- `@RestController` - combines @Controller and @ResponseBody
- `@GetMapping` - maps HTTP GET requests
- `@RequestParam` - binds query parameters with defaults

**2.3 Externalized Configuration**
- application.yml for configuration
- Profile-specific properties support
- Property binding to configuration classes

**2.4 Actuator Integration**
- Health and info endpoints exposed
- Enables monitoring and management

#### Project Structure
```
hello-world-basics/
├── build.gradle.kts                                    # Application build config
├── src/main/java/com/javastarterkit/helloworld/
│   ├── HelloWorldApplication.java                     # Main application class
│   └── web/
│       └── HelloController.java                       # REST endpoint
└── src/main/resources/
    └── application.yml                                # Application configuration
```

#### Endpoints

- `GET /hello?name=World` - Returns greeting message
- `GET /greet` - Returns welcome message

---

## 🎓 Learning Path

### Beginner
1. Start with `hello-world-basics`
   - Understand the basic Spring Boot application structure
   - Learn how to create REST endpoints
   - Experiment with externalized configuration

### Intermediate
2. Study `greeting-service` library
   - Understand how auto-configuration works
   - Learn the starter pattern
   - See how configuration properties bind
   - Practice creating reusable libraries

### Advanced
3. Extend the examples
   - Add more configuration properties to `GreetingProperties`
   - Create custom condition annotations
   - Add Actuator endpoints to the service
   - Implement multiple greeting strategies

---

## 🔧 Development Patterns

### Gradle Build Configuration

Both modules use Spring Boot convention plugins:
```kotlin
plugins {
    id("spring-boot-application-convention")  // For applications
    id("spring-boot-library-convention")       // For libraries
}
```

### Directory Structure (Standard Spring Boot)
```
project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/package/
│   │   │       ├── MainApplication.java
│   │   │       ├── service/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       └── config/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── META-INF/
│   │           └── spring.factories (for libraries)
│   └── test/
│       └── java/
│           └── com/example/package/
```

### Naming Conventions
- **Classes**: PascalCase (e.g., `GreetingService`)
- **Methods**: camelCase (e.g., `greetWithPrefix`)
- **Packages**: all lowercase (e.g., `com.javastarterkit.greeting.service`)
- **Properties**: camelCase (e.g., `includeTime`)

### Testing Patterns
- Use AssertJ for fluent assertions
- One test method per behavior scenario
- Descriptive test names with BDD-style wording
- `@Test` annotation from JUnit 5

---

## 📖 Key Spring Boot Concepts Covered

| Concept | Implementation |
|---------|----------------|
| Dependency Injection | Constructor injection via `@Autowired` or single constructor |
| Configuration Properties | `@ConfigurationProperties` with type-safe binding |
| Auto-Configuration | `spring.factories` registration |
| Conditional Beans | `@ConditionalOnMissingBean`, `@ConditionalOnProperty` |
| REST Controllers | `@RestController`, `@GetMapping`, `@RequestParam` |
| Externalized Config | `application.yml` with hierarchical properties |
| Actuator | Health and info endpoints |
| Gradle Build | Convention plugins, dependency management |

---

## 🚀 Build and Run

### Prerequisites
- Java 17+
- Gradle (wrapper included)

### Build All Modules
```bash
./gradlew build
```

### Run Hello World Basics
```bash
./gradlew :apps:hello-world-basics:bootRun
```

### Access the Application
```bash
# Greeting endpoint
curl http://localhost:8081/hello?name=Developer

# Welcome endpoint
curl http://localhost:8081/greet

# Actuator health
curl http://localhost:8081/actuator/health
```

### Run Tests
```bash
./gradlew test
```

---

## 🎯 Study Exercises

### Exercise 1: Extend Greeting Service
Add support for multiple languages to `GreetingService`:
- Add `language` property to `GreetingProperties`
- Implement language-specific greetings in `DefaultGreetingService`
- Write tests for at least 3 languages

### Exercise 2: Add Actuator Endpoint
Create a custom Actuator endpoint for the greeting service:
- Expose current greeting configuration
- Allow runtime updates to prefix/suffix
- Secure the endpoint

### Exercise 3: Create a New Starter
Build a complete starter for a different domain:
- Choose a domain (e.g., notifications, logging, caching)
- Follow the pattern from `greeting-service`
- Include configuration properties, auto-configuration, and tests

### Exercise 4: Add Validation
Add JSR-380 validation to `GreetingProperties`:
- Validate prefix is not too long (max 50 chars)
- Validate suffix is present
- Write tests for validation failures

---

## 📚 Additional Resources

- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Boot Auto-Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-auto-configuration.html)
- [Configuration Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-configuration.typesafe-configuration-properties)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)

---

## 🔑 Key Takeaways

1. **Convention Over Configuration**: Spring Boot reduces boilerplate through sensible defaults
2. **Starter Pattern**: Dependencies are grouped by functionality (web, data, security, etc.)
3. **Auto-Configuration**: Libraries can provide automatic setup via `spring.factories`
4. **Externalized Configuration**: Use `application.yml` for environment-specific settings
5. **Conditional Beans**: Components are only created when needed (`@ConditionalOn*`)
6. **Testing**: Comprehensive tests ensure reliability; use slices for faster feedback
7. **Build Logic**: Convention plugins standardize builds across modules

---

*This study material is part of the Java Starter Kit project, demonstrating production-ready Spring Boot patterns.*