// ============================================================================
// IoC Container Example — Spring Boot Application
// ============================================================================
// Demonstrates Spring's Inversion of Control Container concepts including:
// - Constructor and Setter-based dependency injection
// - Bean scopes (Singleton, Prototype)
// - Bean lifecycle callbacks
// - Configuration classes and @Bean methods
// - Component scanning
// ============================================================================

import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring IoC Container Example Application"

dependencies {
    // Web starter for REST endpoints
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Boot test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

// Configure Spring Boot extension
springBoot {
    mainClass = "com.javastarterkit.ioc.IocContainerApplication"
}
