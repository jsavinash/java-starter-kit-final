// ============================================================================
// Spring Cloud Circuit Breaker — Example Application
// ============================================================================
// Demonstrates Spring Cloud Circuit Breaker concepts:
// - Resilience4j circuit breaker integration
// - Retry, fallback, and rate limiting
// - Bulkhead pattern for thread isolation
// - Timeout configuration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Circuit Breaker Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Circuit breaker starter
    implementation(libs.findLibrary("spring-cloud-starter-circuitbreaker-resilience4j").get())

    // Actuator
    implementation(libs.findLibrary("spring-boot-starter-actuator").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.circuitbreaker.CircuitBreakerApplication"
}
