// ============================================================================
// IDE Plugin Platform - IntelliJ IDEA plugin development
// ============================================================================

plugins {
    id("platforms.java-conventions")
    alias(libs.plugins.intellij.plugin)
    `java-library`
}

description = "IDE Plugin platform (IntelliJ)"

intellijPlatform {
    pluginConfiguration {
        name = "Java Starter Kit Plugin"
        version = "1.0.0"
        description = "IntelliJ plugin for Java Starter Kit"
        vendor {
            name = "Java Starter Kit"
            url = "https://github.com/jsavinash/java-starter-kit-final"
        }
    }
}

dependencies {
    implementation(project(":core"))

    // Logging
    implementation(libs.slf4j.api)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}