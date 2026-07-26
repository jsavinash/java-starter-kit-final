// ============================================================================
// Android Platform - Android application library
// NOTE: AGP 8.7.3 is not compatible with Gradle 9.x.
//       Uncomment and test with a compatible AGP version when building.
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
}

description = "Android platform (placeholder - needs AGP compatible with Gradle 9.x)"

dependencies {
    implementation(project(":core"))

    // Logging
    implementation(libs.slf4j.api)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}
