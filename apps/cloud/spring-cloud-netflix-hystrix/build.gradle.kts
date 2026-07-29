// ============================================================================
// Spring Cloud Netflix Hystrix — Example Application
// ============================================================================
// Demonstrates Spring Cloud Netflix Hystrix concepts:
// - Circuit breaker pattern
// - Fault tolerance and resilience
// - Fallback methods
// - Service degradation
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Netflix Hystrix Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Cloud Netflix Hystrix
    implementation(libs.findLibrary("spring-cloud-starter-netflix-hystrix").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.hystrix.HystrixApplication"
}