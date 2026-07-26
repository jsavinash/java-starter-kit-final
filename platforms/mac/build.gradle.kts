// ============================================================================
// macOS Platform - macOS native application (Java-based)
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
    application
}

description = "macOS platform"

application {
    mainClass = "com.starterkit.mac.Main"
}

dependencies {
    implementation(project(":core"))

    // Logging
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}