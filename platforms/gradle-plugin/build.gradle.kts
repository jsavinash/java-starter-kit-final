// ============================================================================
// Gradle Plugin Platform - Custom Gradle plugin development
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-gradle-plugin`
}

description = "Gradle Plugin platform"

gradlePlugin {
    website = "https://github.com/jsavinash/java-starter-kit-final"
    vcsUrl = "https://github.com/jsavinash/java-starter-kit-final.git"

    plugins {
        register("starterKitPlugin") {
            id = "com.starterkit.gradle"
            implementationClass = "com.starterkit.gradle.StarterKitPlugin"
            displayName = "Java Starter Kit Gradle Plugin"
            description = "Convention plugin for Java Starter Kit monorepo"
            tags = listOf("java", "starter-kit", "conventions")
        }
    }
}

dependencies {
    // Logging
    implementation(libs.slf4j.api)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}