# Java Starter Kit

A modern Gradle monorepo for Java 25 applications with Spring Boot 4.0, featuring convention plugins, a platform BOM composite build, containerization support, code generation, database migration, and comprehensive code quality/security tooling.

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
| Lombok | **1.18.38** |
| MapStruct | **1.6.3** |
| SpringDoc OpenAPI | **2.8.6** |
| Flyway | **11.6.0** |
| Checkstyle | **10.23.0** |
| SpotBugs | **6.1.2** |
| JaCoCo | **0.8.12** |
| OWASP Dependency Check | **12.1.0** |
| Google Jib | **3.4.5** |
| SonarQube Scanner | **6.1.0.5364** |

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
│   ├── checkstyle/                       # Checkstyle configuration
│   │   ├── checkstyle.xml
│   │   └── checkstyle-suppressions.xml
│   ├── spotbugs/                         # SpotBugs configuration
│   │   └── spotbugs-exclude.xml
│   ├── owasp/                            # OWASP Dependency Check configuration
│   │   └── dependency-check-suppressions.xml
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
| `GET /actuator/info` | Build info and git commit details |
| `GET /swagger-ui.html` | SpringDoc OpenAPI UI (code-first) |
| `GET /v3/api-docs` | OpenAPI spec in JSON format |

## Build Commands

### Core Build & Packaging

| Command | Description |
|---|---|
| `./gradlew clean build` | Full clean build with tests and all checks |
| `./gradlew :apps:webapp:bootRun` | Run the example web application |
| `./gradlew :apps:webapp:jibBuildTar` | Build Docker image as tarball (no Docker daemon needed) |
| `./gradlew :apps:webapp:jibDockerBuild` | Build Docker image to local daemon |
| `./gradlew :apps:webapp:jib` | Build and push Docker image to registry |
| `./gradlew :apps:webapp:bootBuildImage` | Build OCI image via Spring Boot's built-in support |

### Code Generation & Architecture

| Command | Description |
|---|---|
| `./gradlew compileJava` | Compile with Lombok, MapStruct annotation processors |
| `./gradlew :apps:webapp:generateGitProperties` | Generate git.properties for Actuator `/info` |
| API-first: Use OpenAPI Generator (coming soon) | Generate stubs from OpenAPI specs |

### Code Quality & Formatting

| Command | Description |
|---|---|
| `./gradlew spotlessApply` | Auto-format all code (Java, Kotlin, Gradle) |
| `./gradlew spotlessCheck` | Check code formatting only |
| `./gradlew checkstyleMain` | Run Checkstyle on main sources |
| `./gradlew checkstyleTest` | Run Checkstyle on test sources |
| `./gradlew spotbugsMain` | Run SpotBugs on main sources |
| `./gradlew spotbugsTest` | Run SpotBugs on test sources |
| `./gradlew check` | Run all checks (tests + all quality checks) |

### Testing & Coverage

| Command | Description |
|---|---|
| `./gradlew :apps:webapp:test` | Run tests for the web application |
| `./gradlew jacocoTestReport` | Generate JaCoCo code coverage report (HTML + XML) |
| `./gradlew jacocoCoverageVerification` | Verify coverage thresholds |

### Security

| Command | Description |
|---|---|
| `./gradlew dependencyCheckAnalyze` | Scan dependencies for CVEs (OWASP) |
| `./gradlew dependencyCheckUpdate` | Update NVD data feed |

### SonarQube

| Command | Description |
|---|---|
| `./gradlew sonar` | Run SonarQube analysis (requires `SONAR_HOST_URL` and `SONAR_TOKEN` env vars) |

### Utility

| Command | Description |
|---|---|
| `./gradlew dependencies` | Display dependency tree |
| `./gradlew :apps:webapp:bootRun --args='--debug'` | Run app in debug mode |

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
  → Spotless (Palantir Java Format) + ktlint
  → Checkstyle (Google-style rules)
  → SpotBugs bytecode analysis
  → JaCoCo code coverage
  → OWASP Dependency Check (CVE scanning)
  → SonarQube integration
  → License headers

com.javastarterkit.buildlogic.spring-boot-application
  → Applies java-base + testing + code-quality
  → Spring Boot + Dependency Management plugins
  → BootJar, bootRun, devtools, actuator
  → Jib containerization (OCI/Docker images)
  → Git Commit ID plugin (git.properties)
  → Lombok, MapStruct, SpringDoc, Flyway

com.javastarterkit.buildlogic.spring-boot-library
  → Applies java-base + testing + code-quality
  → Spring Dependency Management (no boot plugin)
  → Import platform BOM
  → Lombok, MapStruct, SpringDoc
```

### Version Catalog

All dependency and plugin versions are centralized in `gradle/libs.versions.toml` and accessed via `libs.findLibrary("...")` in both build scripts and convention plugins. Only dependencies actually used by the project are declared.

The Spring Boot starters (`spring-boot-starter`, `spring-boot-starter-web`, etc.) and JUnit platform dependencies are declared in the catalog without explicit versions since they are managed by the `spring-boot` BOM.

## Integrated Plugins

### 🛠️ Core Build & Packaging

| Plugin | Purpose |
|---|---|
| **Spring Boot Gradle Plugin** | Executable JAR packaging, dependency management, application lifecycle |
| **Google Jib** | Build optimized Docker/OCI images without Docker daemon or Dockerfile |
| **Git Commit ID Plugin** | Exposes git metadata (commit hash, branch, tags) in `git.properties` for Actuator `/info` |

### 🎨 Code Generation & Architecture

| Plugin/Dependency | Purpose |
|---|---|
| **Lombok** | Boilerplate reduction via annotations (`@Data`, `@Builder`, `@Slf4j`, etc.) |
| **MapStruct** | Type-safe bean mapping code generation (Entity ↔ DTO) |
| **SpringDoc OpenAPI** | Code-first OpenAPI 3.0 spec generation + Swagger UI |
| **Flyway** | Database migration management (versioned SQL scripts) |

### 🛡️ Code Quality, Security & Testing

| Plugin/Tool | Purpose |
|---|---|
| **Spotless** | Enforce consistent code formatting (Palantir Java Format) |
| **Checkstyle** | Enforce coding standards and style rules |
| **SpotBugs** | Static bytecode analysis to detect bugs and vulnerabilities |
| **JaCoCo** | Code coverage measurement with HTML/XML reports |
| **OWASP Dependency Check** | Scan dependencies against NVD for known CVEs |
| **SonarQube Scanner** | Ship code metrics to centralized SonarQube dashboard |

## Code Quality

- **Spotless** enforces consistent code formatting using Palantir Java Format (Java), ktlint (Kotlin), and ktlint for Gradle Kotlin DSL.
- **Checkstyle** enforces coding standards based on Google Java Style.
- **SpotBugs** performs static bytecode analysis to detect potential bugs.
- **JaCoCo** measures test coverage and generates detailed reports.
- **OWASP Dependency Check** scans all dependencies for known security vulnerabilities.
- All quality checks are wired into the `check` lifecycle and run during `build`.
- Run `./gradlew spotlessApply` to auto-fix formatting issues.
- License headers are enforced on all Java source files.

## Containerization

This project uses **Google Jib** to build container images without requiring a Docker daemon:

```bash
# Build image as tarball (no Docker daemon required)
./gradlew :apps:webapp:jibBuildTar

# Build image to local Docker daemon
./gradlew :apps:webapp:jibDockerBuild

# Build and push image to a container registry
./gradlew :apps:webapp:jib -Djib.to.image=myregistry.com/myapp:latest
```

Jib is configured to:
- Use Amazon Corretto 25 base image (`amazoncorretto:25-al2023`)
- Set JVM flags (`-XX:+UseZGC`, memory limits)
- Expose port 8080
- Use OCI image format
- Tag images with project version and `latest`

## Database Migrations

This project includes **Flyway** for database migration management. Create migration scripts in:

```
apps/webapp/src/main/resources/db/migration/
```

Migrate automatically on application startup. Flyway is auto-configured by Spring Boot.

## Environment Variables

| Variable | Purpose | Default |
|---|---|---|
| `NVD_API_KEY` | OWASP Dependency Check NVD API key (recommended for faster scans) | (empty) |
| `SONAR_HOST_URL` | SonarQube server URL | `http://localhost:9000` |
| `SONAR_TOKEN` | SonarQube authentication token | (empty) |

## Migrating from a Standard Build

If you are familiar with a simpler Gradle setup, this project introduces:

1. **Composite builds** for convention plugins and platform BOM — build logic is reusable across projects.
2. **Version catalog** (`libs.versions.toml`) — all versions declared once.
3. **Custom convention plugins** — apply them via short IDs like `com.javastarterkit.buildlogic.spring-boot-application`.
4. **Comprehensive plugin ecosystem** — Jib, Checkstyle, SpotBugs, JaCoCo, OWASP, SonarQube integrated.
5. **Code generation** — Lombok and MapStruct annotation processors.
6. **OpenAPI documentation** — SpringDoc auto-generates API docs.

## Troubleshooting

### JUnit Platform Version Conflict

If you see `The following conflicting versions were detected: org.junit.platform.commons: 1.11.x, org.junit.platform.launcher: 1.12.x`, ensure `junit-platform-launcher` in `libs.versions.toml` matches the version managed by Spring Boot's BOM.

### OWASP Dependency Check Slowness

The first run downloads the NVD data feed which is ~1GB. Set `NVD_API_KEY` environment variable for faster incremental updates.

### Gradle Daemon Issues

```bash
./gradlew --stop   # Stop all daemons
./gradlew clean build  # Fresh build with new daemon
```

### SpotBugs / Checkstyle False Positives

Add exclusions in:
- `gradle/spotbugs/spotbugs-exclude.xml`
- `gradle/checkstyle/checkstyle-suppressions.xml`

## License

Copyright © 2026 Java Starter Kit. All rights reserved.