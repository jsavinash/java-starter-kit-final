// ============================================================================
// Spring Cloud Consul — Example Application
// ============================================================================
// Demonstrates Spring Cloud Consul concepts:
// - Service discovery with Consul
// - Configuration management
// - Health checks
// - Service registration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Consul Example"

dependencies {
    // Spring Cloud Consul Discovery
    implementation(libs.findLibrary("spring-cloud-starter-consul-discovery").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.consul.ConsulApplication"
}