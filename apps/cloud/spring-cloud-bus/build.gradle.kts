// ============================================================================
// Spring Cloud Bus — Example Application
// ============================================================================
// Demonstrates Spring Cloud Bus concepts:
// - Distributed messaging for microservices
// - Event broadcasting across services
// - Dynamic configuration refresh
// - Bus endpoints for management
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Bus Example"

dependencies {
    // Spring Cloud Bus
    implementation(libs.findLibrary("spring-cloud-starter-bus-amqp").get())

    // Actuator for bus endpoints
    implementation(libs.findLibrary("spring-boot-starter-actuator").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.bus.BusApplication"
}