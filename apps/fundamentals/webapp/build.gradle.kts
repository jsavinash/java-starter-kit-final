// ============================================================================
// Web Application — Spring Boot Example
// ============================================================================
// Demonstrates how to use the convention plugins and platform BOM
// defined in the monorepo's composite builds.
// ============================================================================

import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Example Spring Boot Web Application"

dependencies {
    // Web starter — version managed by the Spring Boot BOM from the platform
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Boot test starter (includes JUnit Jupiter + Mockito from convention)
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

// Configure Spring Boot extension
springBoot {
    mainClass = "com.javastarterkit.webapp.WebApplication"
}
