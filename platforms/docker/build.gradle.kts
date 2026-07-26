// ============================================================================
// Docker Platform - Docker container management library
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
}

description = "Docker platform"

dependencies {
    implementation(project(":core"))

    // Docker Java client
    implementation(libs.docker.java)
    implementation(libs.docker.java.transport.okhttp)

    // Logging
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    // JSON
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}