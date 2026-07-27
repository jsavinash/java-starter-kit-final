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
        id("org.springframework.boot") version "3.4.4"
        id("io.spring.dependency-management") version "1.1.7"
        id("com.diffplug.spotless") version "8.8.0"
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
include("webapp")