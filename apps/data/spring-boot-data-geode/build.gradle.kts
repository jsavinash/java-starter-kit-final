// ============================================================================
// Spring Boot Data Geode — Example Application
// ============================================================================
// Demonstrates Spring Boot Data Geode concepts:
// - Apache Geode/GemFire integration
// - Distributed caching
// - Region management
// - Data access with Geode
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Boot Data Geode Example"

dependencies {
    // Spring Boot Data Geode
    implementation(libs.findLibrary("spring-boot-starter-data-geode").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.geode.GeodeApplication"
}
