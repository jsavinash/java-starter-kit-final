// ============================================================================
// Spring Cloud Netflix Hystrix — Example Application
// ============================================================================
// Demonstrates circuit breaker concepts:
// - Circuit breaker pattern (modern replacement for Hystrix)
// - Fault tolerance and resilience
// - Fallback methods
// - Service degradation
// ============================================================================
// Note: Hystrix is end-of-life. This module now uses Spring Cloud Circuit
// Breaker with Resilience4j as the modern replacement.
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Cloud Circuit Breaker Example (formerly Hystrix)"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Cloud Circuit Breaker with Resilience4j (modern Hystrix replacement)
    implementation(libs.findLibrary("spring-cloud-starter-circuitbreaker-resilience4j").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.hystrix.HystrixApplication"
}
