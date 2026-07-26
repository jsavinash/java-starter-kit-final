// ============================================================================
// platforms.java-conventions - Shared convention plugin for all platforms
// Enforces Java 25 toolchain and common configurations.
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
dependencies {
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
