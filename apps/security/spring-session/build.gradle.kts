// ============================================================================
// Spring Session — Example Application
// ============================================================================
// Demonstrates Spring Session concepts:
// - Session management with Redis
// - Distributed sessions
// - Session repository
// - @EnableJdbcHttpSession
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Session Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Session with Redis
    implementation(libs.findLibrary("spring-session-data-redis").get())

    // Spring Data Redis
    implementation(libs.findLibrary("spring-boot-starter-data-redis").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.session.SessionApplication"
}