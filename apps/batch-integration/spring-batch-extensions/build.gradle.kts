// ============================================================================
// Spring Batch Extensions — Example Application
// ============================================================================
// Demonstrates Spring Batch Extensions concepts:
// - Extended batch processing capabilities
// - Additional job repository options
// - Async job processing
// - Batch administration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Batch Extensions Example"

dependencies {
    // Spring Batch core
    implementation(libs.findLibrary("spring-boot-starter-batch").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.batchextensions.BatchExtensionsApplication"
}
