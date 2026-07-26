// ============================================================================
// Web UI Platform - Frontend web application (Java-based backend)
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
}

description = "Web UI platform (Java backend for frontend)"

dependencies {
    implementation(project(":core"))

    // Logging
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    // JSON
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)

    // HTTP
    implementation(libs.okhttp)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}