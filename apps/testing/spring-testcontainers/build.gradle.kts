// ============================================================================
// Spring Testcontainers — Example Application
// ============================================================================
// Demonstrates Testcontainers integration concepts:
// - Database container testing
// - Integration tests with real services
// - Container lifecycle management
// - Testcontainers with @DynamicPropertySource
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Testcontainers Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Testcontainers
    testImplementation(libs.findLibrary("testcontainers").get())
    testImplementation(libs.findLibrary("testcontainers-postgresql").get())
    testImplementation(libs.findLibrary("testcontainers-junit-jupiter").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.testcontainers.TestcontainersApplication"
}