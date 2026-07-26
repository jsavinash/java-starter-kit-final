// ============================================================================
// iOS Platform - iOS application (Kotlin Multiplatform placeholder)
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
}

description = "iOS platform (Kotlin Multiplatform placeholder)"

dependencies {
    implementation(project(":core"))

    // Logging
    implementation(libs.slf4j.api)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}