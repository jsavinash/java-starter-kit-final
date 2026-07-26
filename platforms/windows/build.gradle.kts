// ============================================================================
// Windows Platform - Windows native application (Java-based)
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
    application
}

description = "Windows platform"

application {
    mainClass = "com.starterkit.windows.Main"
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