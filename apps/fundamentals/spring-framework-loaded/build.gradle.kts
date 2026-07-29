// ============================================================================
// Spring Framework Loaded — Example Application
// ============================================================================
// Demonstrates Spring Loaded concepts:
// - Runtime class reloading
// - Development-time code changes
// - JVM agent for hot swapping
// - Application context refresh
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Loaded Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Loaded agent
    implementation(libs.findLibrary("spring-loaded").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.loaded.LoadedApplication"
}
