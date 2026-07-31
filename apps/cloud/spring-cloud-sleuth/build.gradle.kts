// ============================================================================
// Spring Cloud Sleuth — Example Application
// ============================================================================
// Demonstrates distributed tracing concepts:
// - Distributed tracing (modern replacement for Sleuth)
// - Correlation IDs and span management
// - Integration with Zipkin
// - Log correlation
// ============================================================================
// Note: Spring Cloud Sleuth is end-of-life. This module now uses Micrometer
// Tracing (built into Spring Boot 4.x) as the modern replacement.
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Micrometer Tracing Example (formerly Sleuth)"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Actuator for tracing endpoints
    implementation(libs.findLibrary("spring-boot-starter-actuator").get())

    // Micrometer Tracing with Zipkin (managed by Spring Boot BOM)
    implementation(libs.findLibrary("micrometer-tracing-bridge-brave").get())
    implementation(libs.findLibrary("zipkin-reporter-brave").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.sleuth.SleuthApplication"
}
