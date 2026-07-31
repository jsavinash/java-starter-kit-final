// ============================================================================
// Spring Boot Actuator & DevTools — Example Application
// ============================================================================
// Demonstrates Spring Boot Actuator and DevTools concepts:
// - Actuator endpoints for monitoring and management
// - Custom actuator indicators
// - DevTools for hot reloading and live reload
// - Production-ready features
// ============================================================================

import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Boot Actuator & DevTools Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Actuator for monitoring and management
    implementation(libs.findLibrary("spring-boot-starter-actuator").get())

    // DevTools for development
    implementation(libs.findLibrary("spring-boot-devtools").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.actuator.ActuatorApplication"
}
