// ============================================================================
// CLI Platform - Command-line applications using Picocli
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
    application
}

description = "CLI application platform"

application {
    mainClass = "com.starterkit.cli.Main"
}

dependencies {
    implementation(project(":core"))

    // CLI framework
    implementation(libs.picocli)

    // Logging
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
    testImplementation(libs.mockito.core)
}