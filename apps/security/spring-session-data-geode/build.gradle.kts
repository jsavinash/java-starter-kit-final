// ============================================================================
// Spring Session Data Geode — Example Application
// ============================================================================
// Demonstrates Spring Session Data Geode concepts:
// - Session management with Apache Geode
// - Distributed session storage
// - Session expiration and cleanup
// - Geode region-backed sessions
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Session Data Geode Example"

dependencies {
    // Spring Web
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Session Data Geode
    implementation(libs.findLibrary("spring-session-data-geode").get())

    // Spring Boot Data Geode
    implementation(libs.findLibrary("spring-boot-starter-data-geode").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.sessiongeode.SessionGeodeApplication"
}