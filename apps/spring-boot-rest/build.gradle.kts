// ============================================================================
// Spring Boot REST APIs — Example Application
// ============================================================================
// Demonstrates Spring Boot REST API concepts:
// - REST controllers and request mapping
// - Validation with jakarta.validation
// - Exception handling
// - DTOs and request/response models
// ============================================================================

import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Boot REST APIs Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Validation starter
    implementation(libs.findLibrary("spring-boot-starter-validation").get())

    // H2 database for demo data
    implementation(libs.findLibrary("h2").get())

    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.rest.SpringBootRestApplication"
}