# Spring Feature Flags — Example Application

A Spring Boot application demonstrating a production-ready feature flag management system with runtime evaluation, REST API, and Actuator health integration.

## 🎯 Purpose

Showcases Spring Boot auto-configuration and extension patterns with a focus on:
- Layered architecture (Controller → Service → Repository → Model)
- `@ConfigurationProperties` for externalized configuration
- `CommandLineRunner` for startup initialization
- Actuator `HealthIndicator` integration
- Percentage-based rollout strategies with consistent hashing
- REST API for runtime flag management

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web layer (REST endpoints)
- `spring-boot-starter-validation` — Bean validation
- `spring-boot-starter-actuator` — Health endpoint (via convention plugin)

## 🚀 How to Run

```bash
./gradlew :apps:extensions:spring-feature-flags:bootRun
```

Then visit:
- `http://localhost:8080/api/feature-flags` — List all flags
- `http://localhost:8080/api/feature-flags/{name}/enabled` — Check flag state
- `http://localhost:8080/actuator/health` — Health with flag counts

## 📚 Concepts Demonstrated

- **Layered Architecture** — Controller → Service → Repository → Model
- **Configuration Properties** — `@ConfigurationProperties(prefix = "app.feature-flags")`
- **Startup Initialization** — `CommandLineRunner` seeds default flags from YAML
- **Rollout Strategies** — `GLOBAL`, `PERCENTAGE`, `USER_IDS`, `USER_ATTRIBUTES`
- **Consistent Hashing** — Same user always gets the same rollout result
- **Actuator Integration** — Custom `HealthIndicator` reports flag statistics
- **REST API** — Full CRUD for feature flag management
- **Constructor Injection** — All beans use constructor-based dependency injection
- **Null-safety** — `Objects.requireNonNull` and input validation throughout

## 🗂️ Module Structure

```
spring-feature-flags/
└── src/
    ├── main/
    │   ├── java/com/javastarterkit/featureflags/
    │   │   ├── FeatureFlagsApplication.java
    │   │   ├── actuator/
    │   │   │   └── FeatureFlagHealthIndicator.java
    │   │   ├── config/
    │   │   │   ├── FeatureFlagInitializer.java
    │   │   │   └── FeatureFlagProperties.java
    │   │   ├── model/
    │   │   │   ├── FeatureFlag.java
    │   │   │   └── RolloutStrategy.java
    │   │   ├── repository/
    │   │   │   ├── FeatureFlagRepository.java
    │   │   │   └── InMemoryFeatureFlagRepository.java
    │   │   ├── service/
    │   │   │   └── FeatureFlagService.java
    │   │   └── web/
    │   │       └── FeatureFlagController.java
    │   └── resources/
    │       └── application.yml
    └── test/
        └── java/com/javastarterkit/featureflags/
            ├── actuator/
            │   └── FeatureFlagHealthIndicatorTest.java
            ├── model/
            │   └── FeatureFlagTest.java
            ├── repository/
            │   └── InMemoryFeatureFlagRepositoryTest.java
            ├── service/
            │   └── FeatureFlagServiceTest.java
            └── web/
                └── FeatureFlagControllerTest.java
```

## ⚙️ Configuration

Feature flags are defined in `application.yml` under the `app.feature-flags` prefix:

```yaml
app:
  feature-flags:
    enabled: true
    defaults:
      new-ui:
        enabled: true
        strategy: GLOBAL
        rollout-percentage: 100
        description: "Enable the redesigned user interface"
      beta-dashboard:
        enabled: false
        strategy: PERCENTAGE
        rollout-percentage: 25
        description: "Beta dashboard for early access users"