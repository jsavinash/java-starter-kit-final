// ============================================================================
// Spring Cloud Netflix Eureka — Example Application
// ============================================================================
// Demonstrates Spring Cloud Netflix Eureka concepts:
// - Service discovery and registration
// - Client-side load balancing
// - Health checks
// - Service registry
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Netflix Eureka Example"

dependencies {
    // Spring Cloud Netflix Eureka Server
    implementation(libs.findLibrary("spring-cloud-starter-netflix-eureka-server").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.eureka.EurekaApplication"
}
