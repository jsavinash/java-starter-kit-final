// ============================================================================
// Spring Boot Starters & Auto-configuration — Example Application
// ============================================================================
// Demonstrates Spring Boot's starter dependencies and auto-configuration:
// - Spring Boot starters and their purpose
// - Auto-configuration classes
// - Custom auto-configuration
// - @Conditional annotations
// ============================================================================

import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Boot Starters & Auto-configuration Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // JDBC starter for database connectivity
    implementation(libs.findLibrary("spring-boot-starter-jdbc").get())

    // H2 database for in-memory database
    implementation(libs.findLibrary("h2").get())

    // Actuator for monitoring and management
    implementation(libs.findLibrary("spring-boot-starter-actuator").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.starters.StartersApplication"
}
