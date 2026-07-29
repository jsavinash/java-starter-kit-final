// ============================================================================
// Spring Data Samples — Example Application
// ============================================================================
// Demonstrates Spring Data Samples concepts:
// - Example code for various Spring Data modules
// - Sample implementations
// - Best practices demonstrations
// - Learning resources
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data Samples Example"

dependencies {
    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.datasamples.DataSamplesApplication"
}