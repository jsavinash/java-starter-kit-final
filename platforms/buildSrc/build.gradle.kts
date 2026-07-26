// ============================================================================
// buildSrc - Convention plugins for all platforms
// ============================================================================

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

// Dependencies for convention plugins
// Note: Precompiled script plugins in buildSrc cannot use the version catalog (libs).
// Versions must be specified directly here.
dependencies {
    // Spotless code formatter (Gradle plugin marker artifact)
    implementation("com.diffplug.spotless:com.diffplug.spotless.gradle.plugin:8.8.0")
}
