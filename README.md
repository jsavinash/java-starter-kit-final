# Java Starter Kit

A modern Gradle monorepo for Java 25 applications with Spring Boot 4.0, featuring convention plugins, a platform BOM composite build, and integrated code quality tooling.

## Tech Stack

| Component | Version |
|---|---|
| Java | **25** (Amazon Corretto 25.0.4) |
| Gradle | **9.6.1** |
| Kotlin (DSL) | **2.4.10** |
| Spring Boot | **4.0.7** |
| JUnit Jupiter | **5.12.x** (managed by Spring Boot BOM) |
| AssertJ | **3.27.3** |
| Mockito | **5.16.1** |
| Spotless | **8.8.0** |

## Project Structure

```
java-starter-kit/
├── build-logic/                          # Composite build: convention plugins
│   ├── settings.gradle.kts
│   └── convention-plugins/
│       ├── build.gradle.kts
│       └── src/main/kotlin/.../buildlogic/
│           ├── JavaBaseConventionPlugin.kt
│           ├── SpringBootApplicationConventionPlugin.kt
│           ├── SpringBootLibraryConventionPlugin.kt
│           ├── TestingConventionPlugin.kt
│           └── CodeQualityConventionPlugin.kt
├── platforms/                            # Composite build: BOM platform
│   ├── settings.gradle.kts
│   └── spring-boot/
│       └── build.gradle.kts
├── apps/                                 # Application modules
│   └── webapp/                           # Example Spring Boot application
│       ├── build.gradle.kts
│       └── src/
│           ├── main/java/com/javastarterkit/webapp/
│           │   ├── WebApplication.java
│           │   ├── service/GreetingService.java
│           │   └── web/GreetingController.java
│           ├── main/resources/application.yml
│           └── test/java/com/javastarterkit/webapp/
│               └── WebApplicationTest.java
├── gradle/
│   ├── libs.versions.toml                # Centralized version catalog
│   └── wrapper/
├── settings.gradle.kts
├── .sdkmanrc
└── gradlew
```

## Prerequisites

- **SDKMAN!** (recommended) — The project includes a `.sdkmanrc` that automatically configures the correct toolchain versions:

  ```bash
  # Install SDKMAN! if not already installed:
  curl -s "https://get.sdkman.io" | bash
  
  # Activate the project's SDK versions:
  cd java-starter-kit
  sdk env install
  ```

- Or install manually:
  - Java 25 (Amazon Corretto 25.0.4)
  - Kotlin 2.4.10
  - Gradle 9.6.1

## Quick Start

```bash
# Clone the repository
git clone https://github.com/jsavinash/java-starter-kit-final.git
cd java-starter-kit-final

# Set up SDK versions (requires SDKMAN!)
sdk env install

# Build the entire project
./gradlew clean build

# Run the web application
./gradlew :apps:webapp:bootRun
```

The web application starts on `http://localhost:8080` with the following endpoints:

| Endpoint | Description |
|---|---|
| `GET /api/greeting` | Returns "Hello, World!" with timestamp |
| `GET /api/greeting?name=Java` | Returns "Hello, Java!" with timestamp |
| `GET /api/health` | Returns "UP" |
| `GET /actuator/health` | Spring Boot Actuator health |

## Build Commands

| Command | Description |
|---|---|
| `./gradlew clean build` | Full clean build with tests and checks |
| `./gradlew spotlessApply` | Auto-format all code (Java, Kotlin, Gradle) |
| `./gradlew spotlessCheck` | Check code formatting only |
| `./gradlew :apps:webapp:bootRun` | Run the example web application |
| `./gradlew :apps:webapp:test` | Run tests for the web application |
| `./gradlew check` | Run all checks (tests + formatting) |
| `./gradlew dependencies` | Display dependency tree |

## Architecture

### Composite Builds

The project uses Gradle composite builds for modularity:

1. **`build-logic`** — Convention plugins (`java-base`, `spring-boot-application`, `spring-boot-library`, `testing`, `code-quality`) providing reusable build configurations.
2. **`platforms/spring-boot`** — A Java Platform (BOM) that pins Spring Boot and ecosystem dependency versions centrally.

### Convention Plugin Hierarchy

```
com.javastarterkit.buildlogic.java-base
  → Java toolchain (Java 25, Amazon Corretto)
  → UTF-8 encoding, compiler warnings
  → JUnit Platform test configuration

com.javastarterkit.buildlogic.testing
  → JUnit Jupiter 5 (API, Engine, Params)
  → AssertJ, Mockito
  → JUnit Platform Launcher

com.javastarterkit.buildlogic.code-quality
  → Spotless (Palantir Java Format)
  → License headers
  → ktlint for Kotlin/Gradle files

com.javastarterkit.buildlogic.spring-boot-application
  → Applies java-base + testing + code-quality
  → Spring Boot + Dependency Management plugins
  → BootJar, bootRun, devtools, actuator

com.javastarterkit.buildlogic.spring-boot-library
  → Applies java-base + testing + code-quality
  → Spring Dependency Management (no boot plugin)
  → Import platform BOM
```

### Version Catalog

All dependency and plugin versions are centralized in `gradle/libs.versions.toml` and accessed via `libs.findLibrary("...")` in both build scripts and convention plugins. Only dependencies actually used by the project are declared — unused libraries (Micronaut, Docker, AWS Lambda, Android, etc.) have been removed from the catalog.

The Spring Boot starters (`spring-boot-starter`, `spring-boot-starter-web`, etc.) and JUnit platform dependencies are declared in the catalog without explicit versions since they are managed by the `spring-boot` BOM.

## Code Quality

- **Spotless** enforces consistent code formatting using Palantir Java Format (Java), ktlint (Kotlin), and ktlint for Gradle Kotlin DSL.
- Formatting is checked automatically during `build` via the `check` lifecycle.
- Run `./gradlew spotlessApply` to auto-fix formatting issues.
- License headers are enforced on all Java source files.

## Migrating from a Standard Build

If you are familiar with a simpler Gradle setup, this project introduces:

1. **Composite builds** for convention plugins and platform BOM — build logic is reusable across projects.
2. **Version catalog** (`libs.versions.toml`) — all versions declared once.
3. **Custom convention plugins** — apply them via short IDs like `com.javastarterkit.buildlogic.spring-boot-application`.
4. **Spotless** — auto-formatting as part of the build lifecycle.

## Troubleshooting

### JUnit Platform Version Conflict

If you see `The following conflicting versions were detected: org.junit.platform.commons: 1.11.x, org.junit.platform.launcher: 1.12.x`, ensure `junit-platform-launcher` in `libs.versions.toml` matches the version managed by Spring Boot's BOM.

### Gradle Daemon Issues

```bash
./gradlew --stop   # Stop all daemons
./gradlew clean build  # Fresh build with new daemon
```

## License

Copyright © 2026 Java Starter Kit. All rights reserved.