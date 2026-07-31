// ============================================================================
// Spring Pulsar — Example Application
// ============================================================================
// Demonstrates Apache Pulsar integration concepts:
// - Topic-based messaging
// - Producer and consumer patterns
// - Message acknowledgment
// - Schema validation
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Pulsar Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Pulsar
    implementation(libs.findLibrary("spring-boot-starter-pulsar").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.pulsar.PulsarApplication"
}
