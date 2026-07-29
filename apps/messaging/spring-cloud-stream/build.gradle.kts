// ============================================================================
// Spring Cloud Stream — Example Application
// ============================================================================
// Demonstrates Spring Cloud Stream concepts:
// - Message-driven microservices
// - Binder abstraction for messaging middleware
// - Consumer and producer patterns
// - Dynamic destination binding
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Stream Example"

dependencies {
    // Spring Cloud Stream
    implementation(libs.findLibrary("spring-cloud-stream").get())

    // RabbitMQ binder
    implementation(libs.findLibrary("spring-cloud-stream-binder-rabbit").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.stream.StreamApplication"
}
