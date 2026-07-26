// ============================================================================
// Linux Platform - Linux native application (Java-based)
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
    application
}

description = "Linux platform"

application {
    mainClass = "com.starterkit.linux.Main"
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