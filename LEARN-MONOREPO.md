# Learning Guide: Building a Java Monorepo with Gradle

> **A comprehensive walkthrough of the Java Starter Kit monorepo architecture**
>
> This document explains **how** and **why** each piece of this monorepo was built, so you can recreate a similar structure for your own projects.

---

## Table of Contents

1. [What is a Monorepo?](#1-what-is-a-monorepo)
2. [Tech Stack Overview](#2-tech-stack-overview)
3. [Project Structure at a Glance](#3-project-structure-at-a-glance)
4. [Core Concept 1: Composite Builds](#4-core-concept-1-composite-builds)
5. [Core Concept 2: Version Catalog (libs.versions.toml)](#5-core-concept-2-version-catalog)
6. [Core Concept 3: Convention Plugins](#6-core-concept-3-convention-plugins)
7. [Core Concept 4: Java Platform / BOM](#7-core-concept-4-java-platform--bom)
8. [Core Concept 5: Application Modules](#8-core-concept-5-application-modules)
9. [Code Quality Tooling](#9-code-quality-tooling)
10. [How to Add a New Module](#10-how-to-add-a-new-module)
11. [How to Add a New Convention Plugin](#11-how-to-add-a-new-convention-plugin)
12. [Build Commands Reference](#12-build-commands-reference)
13. [Troubleshooting Common Issues](#13-troubleshooting-common-issues)
14. [Glossary](#14-glossary)

---

## 1. What is a Monorepo?

A **monorepo** (mono = single, repo = repository) is a single Git repository that contains multiple distinct projects or modules. This is the opposite of a **multirepo** approach where each project lives in its own repository.

### Why use a monorepo?

| Benefit | Description |
|---------|-------------|
| **Shared build logic** | One set of Gradle convention plugins for all modules |
| **Consistent versions** | A single version catalog ensures all modules use the same dependency versions |
| **Atomic commits** | A change that touches multiple modules is a single commit |
| **Cross-module refactoring** | Rename a shared class and all usages update in one commit |
| **Simplified CI/CD** | One pipeline, one artifact repository |
| **Unified tooling** | One Gradle wrapper, one set of quality checks |

### When NOT to use a monorepo

- Teams have very different release cadences
- Modules are truly independent and never share code
- Repository size becomes unmanageable (100+ GB)
- You need fine-grained access control per module

---

## 2. Tech Stack Overview

This monorepo uses the following toolchain (defined in `.sdkmanrc`):

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 25 (Amazon Corretto) | Language runtime |
| **Kotlin** | 2.4.10 | Gradle DSL language |
| **Gradle** | 9.6.1 | Build system |
| **Spring Boot** | 4.0.7 | Application framework |

### SDKMAN! — Toolchain Version Manager

The `.sdkmanrc` file at the project root declares the exact toolchain versions:

```properties
java=25.0.4-amzn
kotlin=2.4.10
gradle=9.6.1
springboot=4.0.7
```

**How it works:**
1. Install [SDKMAN!](https://sdkman.io/)
2. Run `sdk env install` in the project root — SDKMAN! reads `.sdkmanrc` and installs/activates the correct versions
3. Run `sdk env` to switch to the project's toolchain anytime

This ensures every developer (and CI) uses **exactly** the same tool versions.

---

## 3. Project Structure at a Glance

```
java-starter-kit/
│
├── settings.gradle.kts              # Root build configuration
├── .sdkmanrc                        # Toolchain versions
├── gradlew / gradlew.bat            # Gradle wrapper scripts
│
├── gradle/
│   ├── libs.versions.toml           # Centralized version catalog
│   ├── wrapper/                     # Gradle wrapper JAR + properties
│   ├── checkstyle/                  # Checkstyle rules + suppressions
│   ├── spotbugs/                    # SpotBugs exclusion rules
│   └── owasp/                       # OWASP Dependency Check suppressions
│
├── build-logic/                     # COMPOSITE BUILD: Convention plugins
│   ├── settings.gradle.kts
│   ├── gradle.properties
│   └── convention-plugins/
│       ├── build.gradle.kts
│       └── src/main/kotlin/.../buildlogic/
│           ├── JavaBaseConventionPlugin.kt
│           ├── TestingConventionPlugin.kt
│           ├── CodeQualityConventionPlugin.kt
│           ├── SpringBootApplicationConventionPlugin.kt
│           └── SpringBootLibraryConventionPlugin.kt
│
├── platforms/                       # COMPOSITE BUILD: BOM platform
│   ├── settings.gradle.kts
│   ├── gradle.properties
│   └── spring-boot/
│       └── build.gradle.kts
│
└── apps/                            # APPLICATION MODULES
    └── webapp/
        ├── build.gradle.kts
        └── src/
            ├── main/java/.../webapp/
            │   ├── WebApplication.java
            │   ├── service/GreetingService.java
            │   └── web/GreetingController.java
            ├── main/resources/application.yml
            └── test/java/.../webapp/
                └── WebApplicationTest.java
```

### The Three Layers

This monorepo has three distinct layers:

1. **Infrastructure Layer** (`gradle/`, `build-logic/`, `platforms/`) — Build tooling, version management, convention plugins
2. **Platform Layer** (`platforms/spring-boot/`) — BOM that pins dependency versions
3. **Application Layer** (`apps/`) — Actual runnable applications and libraries

---

## 4. Core Concept 1: Composite Builds

### What is a Composite Build?

A **composite build** is a Gradle feature that lets you include one Gradle build inside another. Instead of publishing artifacts to a repository and consuming them, composite builds are resolved at configuration time — they are always in sync.

### Why Composite Builds?

- **No publishing needed** — Convention plugins and BOMs are consumed directly from source
- **Always up-to-date** — Changes to build logic are immediately visible to all modules
- **Separation of concerns** — Build logic lives in its own project with its own build script

### How It's Configured

In the root `settings.gradle.kts`:

```kotlin
// Include the platforms BOM composite build
includeBuild("platforms")

// Include the convention plugins composite build
includeBuild("build-logic")
```

### The `build-logic` Composite Build

`build-logic/settings.gradle.kts`:

```kotlin
rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))  // ← Shares the root version catalog
        }
    }
}

include(":convention-plugins")
```

**Key insight:** The `build-logic` composite build imports the **same** `libs.versions.toml` from the root project. This means convention plugins and application modules share one source of truth for versions.

### The `platforms` Composite Build

`platforms/settings.gradle.kts`:

```kotlin
rootProject.name = "platforms"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include("spring-boot")
```

Same pattern — the platform BOM also reads from the shared version catalog.

---

## 5. Core Concept 2: Version Catalog

### What is a Version Catalog?

A **version catalog** (defined in `gradle/libs.versions.toml`) is a centralized file that declares all dependency coordinates and plugin versions. It replaces hardcoded version strings scattered across `build.gradle.kts` files.

### File Structure

The TOML file has three sections:

```toml
[versions]
# All version numbers declared here
spring-boot = "4.0.7"
assertj = "3.27.3"
lombok = "1.18.38"

[libraries]
# Library coordinates using version references
assertj-core = { module = "org.assertj:assertj-core", version.ref = "assertj" }
lombok = { module = "org.projectlombok:lombok", version.ref = "lombok" }

# Libraries without explicit versions (managed by BOM)
spring-boot-starter-web = { module = "org.springframework.boot:spring-boot-starter-web" }

[plugins]
# Plugin coordinates
spring-boot = { id = "org.springframework.boot", version.ref = "spring-boot" }
spotless = { id = "com.diffplug.spotless", version.ref = "spotless" }
```

### How to Access the Catalog

In any `build.gradle.kts`:

```kotlin
val libs = the<VersionCatalogsExtension>().named("libs")

// Access a library
implementation(libs.findLibrary("spring-boot-starter-web").get())

// Access a version
val javaVersion = libs.findVersion("java-language").get().displayName
```

### Why Use a Version Catalog?

1. **Single source of truth** — Change a version in one place
2. **Type-safe access** — IDE autocompletion for catalog entries
3. **Shared across composite builds** — Both `build-logic` and `platforms` import the same catalog
4. **No duplication** — Every dependency is declared exactly once

---

## 6. Core Concept 3: Convention Plugins

### What is a Convention Plugin?

A **convention plugin** is a precompiled Gradle plugin that encapsulates reusable build configuration. Instead of copying the same `apply plugin:` and `configure { }` blocks into every module's `build.gradle.kts`, you define the configuration once in a plugin and apply it with a single line.

### Plugin Hierarchy

```
com.javastarterkit.buildlogic.java-base
  └─ com.javastarterkit.buildlogic.testing
  └─ com.javastarterkit.buildlogic.code-quality
       └─ com.javastarterkit.buildlogic.spring-boot-application
       └─ com.javastarterkit.buildlogic.spring-boot-library
```

### Plugin 1: JavaBaseConventionPlugin

**Plugin ID:** `com.javastarterkit.buildlogic.java-base`

**What it does:**
- Applies the `java-library` plugin
- Configures Java toolchain (Java 25, Amazon Corretto vendor)
- Sets source/target compatibility
- Configures compiler warnings (`-Xlint:all`, `-parameters`)
- Configures JUnit Platform for all test tasks
- Sets JVM heap sizes for tests (512m min, 2g max)
- Adds Java 25+ compatibility flags (`-XX:+EnableDynamicAgentLoading`)

**Why it exists:** Every Java module needs these settings. Without this plugin, you'd repeat them in every `build.gradle.kts`.

### Plugin 2: TestingConventionPlugin

**Plugin ID:** `com.javastarterkit.buildlogic.testing`

**What it does:**
- Adds JUnit Jupiter 5 (API, Engine, Params)
- Adds JUnit Platform Launcher
- Adds AssertJ for fluent assertions
- Adds Mockito (core + JUnit Jupiter integration)

**Why it exists:** Every module needs testing dependencies. This plugin ensures consistent test library versions across the monorepo.

### Plugin 3: CodeQualityConventionPlugin

**Plugin ID:** `com.javastarterkit.buildlogic.code-quality`

**What it does:**
- **Spotless** — Enforces code formatting (Palantir Java Format for Java, ktlint for Kotlin/Gradle DSL)
- **Checkstyle** — Enforces coding standards (Google-style rules)
- **SpotBugs** — Static bytecode analysis for bug detection
- **JaCoCo** — Code coverage measurement
- **OWASP Dependency Check** — Security vulnerability scanning
- **SonarQube** — Centralized quality dashboard integration
- Wires all checks into the `check` lifecycle task

**Why it exists:** Quality tooling configuration is verbose. This plugin applies and configures six quality tools with a single line.

### Plugin 4: SpringBootApplicationConventionPlugin

**Plugin ID:** `com.javastarterkit.buildlogic.spring-boot-application`

**What it does:**
- Applies `java-base`, `testing`, and `code-quality` plugins
- Applies Spring Boot plugin and Dependency Management plugin
- Applies Jib (containerization) and Git Properties plugins
- Configures BootJar (output JAR name)
- Configures bootRun with remote debugging (port 5005)
- Adds the platform BOM as a dependency constraint
- Adds Spring Boot starters (web, actuator, devtools)
- Adds Lombok, MapStruct, SpringDoc, Flyway dependencies
- Configures annotation processors

**Why it exists:** A Spring Boot application needs ~20+ lines of boilerplate. This plugin reduces it to one line.

### Plugin 5: SpringBootLibraryConventionPlugin

**Plugin ID:** `com.javastarterkit.buildlogic.spring-boot-library`

**What it does:**
- Same as the application plugin, but:
  - Does NOT apply the Spring Boot plugin (no bootJar task)
  - Applies only Dependency Management
  - Adds the BOM as `api` (transitive to consumers)
  - Includes Lombok, MapStruct, SpringDoc

**Why it exists:** Library modules need Spring Boot's dependency management but not the executable JAR packaging.

### How Convention Plugins Are Registered

In `build-logic/convention-plugins/build.gradle.kts`:

```kotlin
gradlePlugin {
    plugins {
        register("javaBaseConventions") {
            id = "com.javastarterkit.buildlogic.java-base"
            displayName = "Java Base Conventions"
            description = "Configures Java language version, encoding, and standard settings"
            implementationClass = "com.javastarterkit.buildlogic.JavaBaseConventionPlugin"
        }
        // ... more plugins registered here
    }
}
```

### How Convention Plugins Access External Plugins

Convention plugins that programmatically apply external plugins (like Spotless or Spring Boot) need those plugins on their classpath:

```kotlin
dependencies {
    implementation(plugin(libs, "spotless"))
    implementation(plugin(libs, "spring-boot"))
    implementation(plugin(libs, "spring-dependency-management"))
    // ...
}

// Helper function to resolve plugin coordinates from the version catalog
fun plugin(libs: VersionCatalog, alias: String): String {
    val plugin = libs.findPlugin(alias).get().get()
    val id = plugin.pluginId
    val version = libs.findVersion(
        when (alias) {
            "spring-boot" -> "spring-boot"
            "spring-dependency-management" -> "spring-dependency-management"
            "spotless" -> "spotless"
            "spotbugs" -> "spotbugs-gradle-plugin"
            else -> alias
        }
    ).get().displayName
    return "$id:$id.gradle.plugin:$version"
}
```

---

## 7. Core Concept 4: Java Platform / BOM

### What is a Java Platform (BOM)?

A **Java Platform** (also called a **BOM** — Bill of Materials) is a special Gradle module that uses the `java-platform` plugin. It doesn't produce a JAR with code — it produces a POM file that declares dependency versions. When other modules import this platform, they inherit all those version constraints.

### Why a Custom BOM?

Spring Boot already has its own BOM (`spring-boot-dependencies`). Why create another one?

1. **Pin additional dependencies** — Spring Boot's BOM doesn't cover everything (e.g., AssertJ, Mockito, Jackson versions may need pinning)
2. **Custom version overrides** — You may need a different version of a library than what Spring Boot provides
3. **Single import point** — Application modules import one BOM (`com.javastarterkit:spring-boot`) instead of remembering multiple BOM coordinates

### How It Works

`platforms/spring-boot/build.gradle.kts`:

```kotlin
plugins {
    `java-platform`
    `maven-publish`
}

javaPlatform {
    allowDependencies()  // Allows importing another BOM
}

dependencies {
    // Import Spring Boot's BOM
    api(platform("org.springframework.boot:spring-boot-dependencies:4.0.7"))

    // Pin additional versions
    constraints {
        api("org.assertj:assertj-core:3.27.3")
        api("org.mockito:mockito-core:5.16.1")
        api("com.fasterxml.jackson.core:jackson-core:2.18.3")
        // ...
    }
}
```

### How Modules Consume the BOM

In `SpringBootApplicationConventionPlugin.kt`:

```kotlin
deps.add("implementation", deps.platform("com.javastarterkit:spring-boot:1.0.0-SNAPSHOT"))
```

This adds the platform BOM as a dependency constraint, meaning all Spring Boot managed dependencies (and the pinned extras) will use the versions declared in the BOM.

---

## 8. Core Concept 5: Application Modules

### The Webapp Module

`apps/webapp/build.gradle.kts`:

```kotlin
plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(libs.findLibrary("spring-boot-starter-web").get())
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.webapp.WebApplication"
}
```

**Notice:** The module's `build.gradle.kts` is only ~20 lines. Without convention plugins, it would be 50-80 lines. The convention plugin handles:
- Java toolchain configuration
- Testing dependencies
- Code quality tools
- Spring Boot plugin configuration
- Jib containerization
- Git properties
- Lombok, MapStruct, SpringDoc, Flyway

### Source Code Structure

```
apps/webapp/src/
├── main/
│   ├── java/com/javastarterkit/webapp/
│   │   ├── WebApplication.java          # @SpringBootApplication entry point
│   │   ├── service/
│   │   │   └── GreetingService.java     # @Service business logic
│   │   └── web/
│   │       └── GreetingController.java  # @RestController endpoints
│   └── resources/
│       └── application.yml              # Spring Boot configuration
└── test/
    └── java/com/javastarterkit/webapp/
        └── WebApplicationTest.java      # Integration tests
```

### Key Patterns Demonstrated

1. **Constructor-based dependency injection** — `GreetingController` receives `GreetingService` via constructor
2. **RESTful endpoints** — `@GetMapping` with `@RequestParam`
3. **Integration testing** — `@SpringBootTest` with random port, `RestClient` for HTTP calls
4. **AssertJ fluent assertions** — `assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)`

---

## 9. Code Quality Tooling

### Spotless (Code Formatting)

Configured in `CodeQualityConventionPlugin.kt`:

```kotlin
spotless {
    java {
        palantirJavaFormat()          // Palantir Java Format
        licenseHeader("// Copyright © \$YEAR Java Starter Kit. All rights reserved.")
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlin {
        ktlint()                      // ktlint for Kotlin
    }
    kotlinGradle {
        ktlint()                      // ktlint for .gradle.kts files
    }
}
```

**Commands:**
- `./gradlew spotlessApply` — Auto-format all code
- `./gradlew spotlessCheck` — Check formatting (runs as part of `check`)

### Checkstyle (Code Style)

Configured in `CodeQualityConventionPlugin.kt`:

```kotlin
checkstyle {
    toolVersion = "10.23.0"
    isIgnoreFailures = false
    configFile = rootProject.file("gradle/checkstyle/checkstyle.xml")
}
```

Rules defined in `gradle/checkstyle/checkstyle.xml`:
- No star imports
- No redundant/unused imports
- Correct modifier order
- Braces and block formatting
- Naming conventions (camelCase, CONSTANT_CASE)
- No multiple variable declarations per line

Suppressions in `gradle/checkstyle/checkstyle-suppressions.xml`:
- Generated sources excluded
- Test classes exempt from Javadoc requirements

### SpotBugs (Static Analysis)

Configured in `CodeQualityConventionPlugin.kt`:

```kotlin
plugins.apply("com.github.spotbugs")
dependencies.add("compileOnly", libs.findLibrary("spotbugs-annotations").get())
```

Exclusions in `gradle/spotbugs/spotbugs-exclude.xml`:
- Generated sources (Lombok, MapStruct)
- Application classes (certain patterns)
- DTO classes (serialization checks)
- Test classes (return value checks)

### JaCoCo (Code Coverage)

Configured in `CodeQualityConventionPlugin.kt`:

```kotlin
jacoco {
    toolVersion = "0.8.12"
}
```

**Commands:**
- `./gradlew jacocoTestReport` — Generate coverage report
- `./gradlew jacocoCoverageVerification` — Verify thresholds

### OWASP Dependency Check (Security)

Configured in `CodeQualityConventionPlugin.kt`:

```kotlin
dependencyCheck {
    failBuildOnCVSS = 8.0f    // Fail build on CVSS >= 8.0 vulnerabilities
    formats = listOf("HTML", "JSON")
}
```

**Commands:**
- `./gradlew dependencyCheckAnalyze` — Scan dependencies
- `./gradlew dependencyCheckUpdate` — Update NVD data

### SonarQube (Quality Dashboard)

Configured in `CodeQualityConventionPlugin.kt`:

```kotlin
plugins.apply("org.sonarqube")
```

**Command:** `./gradlew sonar` (requires `SONAR_HOST_URL` and `SONAR_TOKEN` env vars)

---

## 10. How to Add a New Module

### Adding a New Spring Boot Application

1. Create the module directory:
   ```
   apps/new-service/
   ├── build.gradle.kts
   └── src/main/java/com/javastarterkit/newservice/
       └── NewServiceApplication.java
   ```

2. Write `build.gradle.kts`:
   ```kotlin
   plugins {
       id("com.javastarterkit.buildlogic.spring-boot-application")
   }
   
   group = "com.javastarterkit"
   version = "1.0.0-SNAPSHOT"
   
   dependencies {
       implementation(libs.findLibrary("spring-boot-starter-web").get())
       testImplementation(libs.findLibrary("spring-boot-starter-test").get())
   }
   
   springBoot {
       mainClass = "com.javastarterkit.newservice.NewServiceApplication"
   }
   ```

3. Register in root `settings.gradle.kts`:
   ```kotlin
   include("apps:new-service")
   ```

### Adding a New Library Module

1. Create the module directory:
   ```
   libs/shared-domain/
   ├── build.gradle.kts
   └── src/main/java/com/javastarterkit/shared/
       └── SharedModel.java
   ```

2. Write `build.gradle.kts`:
   ```kotlin
   plugins {
       id("com.javastarterkit.buildlogic.spring-boot-library")
   }
   
   group = "com.javastarterkit"
   version = "1.0.0-SNAPSHOT"
   
   dependencies {
       api(libs.findLibrary("spring-boot-starter").get())
   }
   ```

3. Register in root `settings.gradle.kts`:
   ```kotlin
   include("libs:shared-domain")
   ```

---

## 11. How to Add a New Convention Plugin

### Step 1: Create the Plugin Class

In `build-logic/convention-plugins/src/main/kotlin/com/javastarterkit/buildlogic/`:

```kotlin
package com.javastarterkit.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyCustomConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Configure project here
        project.plugins.apply("java-library")
        // ...
    }
}
```

### Step 2: Register the Plugin

In `build-logic/convention-plugins/build.gradle.kts`:

```kotlin
gradlePlugin {
    plugins {
        register("myCustomConventions") {
            id = "com.javastarterkit.buildlogic.my-custom"
            displayName = "My Custom Conventions"
            description = "Configures custom settings"
            implementationClass = "com.javastarterkit.buildlogic.MyCustomConventionPlugin"
        }
    }
}
```

### Step 3: Apply the Plugin

In any module's `build.gradle.kts`:

```kotlin
plugins {
    id("com.javastarterkit.buildlogic.my-custom")
}
```

---

## 12. Build Commands Reference

### Core Build

| Command | Description |
|---------|-------------|
| `./gradlew clean build` | Full clean build with tests and all quality checks |
| `./gradlew :apps:webapp:bootRun` | Run the web application |
| `./gradlew :apps:webapp:test` | Run tests for a specific module |
| `./gradlew dependencies` | Display dependency tree |

### Containerization

| Command | Description |
|---------|-------------|
| `./gradlew :apps:webapp:jibBuildTar` | Build Docker image as tarball |
| `./gradlew :apps:webapp:jibDockerBuild` | Build Docker image to local daemon |
| `./gradlew :apps:webapp:jib` | Build and push to registry |

### Code Quality

| Command | Description |
|---------|-------------|
| `./gradlew spotlessApply` | Auto-format all code |
| `./gradlew spotlessCheck` | Check formatting only |
| `./gradlew checkstyleMain` | Run Checkstyle on main sources |
| `./gradlew checkstyleTest` | Run Checkstyle on test sources |
| `./gradlew spotbugsMain` | Run SpotBugs on main sources |
| `./gradlew spotbugsTest` | Run SpotBugs on test sources |
| `./gradlew check` | Run all checks (tests + quality) |

### Security

| Command | Description |
|---------|-------------|
| `./gradlew dependencyCheckAnalyze` | Scan dependencies for CVEs |
| `./gradlew dependencyCheckUpdate` | Update NVD data feed |

### Coverage

| Command | Description |
|---------|-------------|
| `./gradlew jacocoTestReport` | Generate coverage report |
| `./gradlew jacocoCoverageVerification` | Verify coverage thresholds |

---

## 13. Troubleshooting Common Issues

### JUnit Platform Version Conflict

**Error:** `The following conflicting versions were detected: org.junit.platform.commons: 1.11.x, org.junit.platform.launcher: 1.12.x`

**Fix:** Ensure `junit-platform-launcher` in `libs.versions.toml` matches the version managed by Spring Boot's BOM. Remove the explicit version from the catalog entry — let the BOM manage it.

### OWASP Dependency Check Slowness

**Issue:** First run downloads ~1GB NVD data feed.

**Fix:** Set `NVD_API_KEY` environment variable for faster incremental updates. Get a free API key from [NVD](https://nvd.nist.gov/developers/request-an-api-key).

### Gradle Daemon Issues

```bash
./gradlew --stop          # Stop all daemons
./gradlew clean build     # Fresh build with new daemon
```

### SpotBugs / Checkstyle False Positives

Add exclusions in:
- `gradle/spotbugs/spotbugs-exclude.xml`
- `gradle/checkstyle/checkstyle-suppressions.xml`

### Convention Plugin Not Found

**Error:** `Plugin with id 'com.javastarterkit.buildlogic.xxx' not found`

**Fix:** Ensure the plugin is registered in `build-logic/convention-plugins/build.gradle.kts` and the composite build is included in root `settings.gradle.kts`.

---

## 14. Glossary

| Term | Definition |
|------|------------|
| **BOM** | Bill of Materials — a POM that declares dependency versions without including the actual dependencies |
| **Composite Build** | A Gradle build that includes another Gradle build, sharing outputs at configuration time |
| **Convention Plugin** | A precompiled Gradle plugin that encapsulates reusable build configuration |
| **Java Platform** | A Gradle module using the `java-platform` plugin to produce a BOM |
| **Monorepo** | A single repository containing multiple distinct projects/modules |
| **SDKMAN!** | A CLI tool for managing parallel versions of SDKs (Java, Kotlin, Gradle, etc.) |
| **Version Catalog** | A centralized TOML file declaring all dependency and plugin versions |
| **Gradle Wrapper** | A script (`gradlew`) that downloads and runs a specific Gradle version, ensuring build reproducibility |

---

## Summary: The Architecture Pattern

```
┌─────────────────────────────────────────────────────────────┐
│                    ROOT settings.gradle.kts                  │
│  includeBuild("build-logic")  includeBuild("platforms")     │
│  include("apps:webapp")                                      │
└─────────────────────────────────────────────────────────────┘
         │                            │
         ▼                            ▼
┌──────────────────┐      ┌──────────────────────────┐
│  build-logic/    │      │  platforms/               │
│  Convention      │      │  Java Platform (BOM)      │
│  Plugins         │      │  Pins Spring Boot +       │
│  (Kotlin DSL)    │      │  ecosystem versions       │
│                  │      │                            │
│  java-base       │      │  spring-boot/             │
│  testing         │      │    build.gradle.kts       │
│  code-quality    │      │    → imports Spring BOM   │
│  spring-boot-app │      │    → adds constraints     │
│  spring-boot-lib │      │                            │
└──────────────────┘      └──────────────────────────┘
         │                            │
         └──────────┬─────────────────┘
                    ▼
┌──────────────────────────────────────┐
│  apps/webapp/                        │
│  build.gradle.kts                    │
│  → applies spring-boot-application   │
│  → imports platform BOM              │
│  → adds web-specific deps           │
└──────────────────────────────────────┘
```

### Key Takeaways

1. **Composite builds** enable modular build logic without publishing artifacts
2. **Version catalogs** provide a single source of truth for dependency versions
3. **Convention plugins** eliminate boilerplate and enforce consistency
4. **Platform BOMs** centralize version management for framework ecosystems
5. **Code quality tooling** is configured once and applied everywhere
6. **SDKMAN!** ensures all developers use identical toolchain versions

---

*Generated from the [Java Starter Kit](https://github.com/jsavinash/java-starter-kit-final) monorepo — a reference implementation of modern Gradle monorepo architecture.*