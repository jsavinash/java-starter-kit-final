// ============================================================================
// Core Platform - Shared library module
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
}

description = "Core shared library for all platforms"

dependencies {
    // Logging
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    // JSON
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
}