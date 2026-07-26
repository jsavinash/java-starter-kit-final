// ============================================================================
// Microservice Platform - Standalone microservice (Spring Boot)
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

description = "Microservice platform (Spring Boot)"

dependencies {
    implementation(project(":core"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Logging
    implementation(libs.slf4j.api)

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}