// ============================================================================
// Java Starter Kit - Monorepo Root Settings
// ============================================================================

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        // Explicitly declare plugins used by convention plugins
        // so they are resolvable when applied from composite builds
        id("org.springframework.boot") version "4.0.7"
        id("io.spring.dependency-management") version "1.1.7"
        id("com.diffplug.spotless") version "8.8.0"
        id("com.google.cloud.tools.jib") version "3.4.5"
        id("com.gorylenko.gradle-git-properties") version "4.0.1"
        id("org.owasp.dependencycheck") version "12.2.2"
        id("org.sonarqube") version "7.3.1.8318"
        id("com.github.spotbugs") version "6.5.9"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "java-starter-kit"

// Platforms BOM composite build
includeBuild("platforms")

// Convention plugins composite build
includeBuild("build-logic")

// ---------------------------------------------------------------------------
// Spring Boot Application — Example Subproject
// ---------------------------------------------------------------------------
include("apps:webapp")