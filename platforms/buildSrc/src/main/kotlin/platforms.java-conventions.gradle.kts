// ============================================================================
// platforms.java-conventions - Shared convention plugin for all platforms
// Enforces Java 25 toolchain, common configurations, and code formatting.
// ============================================================================

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
        vendor.set(JvmVendorSpec.AMAZON)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = 25
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// JUnit Platform Launcher required by Gradle 9.x
// Note: Precompiled script plugins in buildSrc cannot access the version catalog (libs).
// The version is managed centrally in gradle/libs.versions.toml for the main build.
dependencies {
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.4")
}

