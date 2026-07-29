// ============================================================================
// Spring Data DevTools — Example Application
// ============================================================================
// Demonstrates Spring Data DevTools concepts:
// - Development-time tools for Spring Data
// - Repository debugging
// - Query validation
// - Data mapping visualization
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data DevTools Example"

dependencies {
    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // Spring Data DevTools
    implementation(libs.findLibrary("spring-data-dev-tools").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.datadevtools.DataDevToolsApplication"
}