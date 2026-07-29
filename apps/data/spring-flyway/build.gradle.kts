// ============================================================================
// Spring Flyway — Example Application
// ============================================================================
// Demonstrates Flyway database migration concepts:
// - Versioned database migrations
// - Schema evolution and management
// - Repeatable migrations
// - Integration with Spring Boot
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Flyway Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // Flyway
    implementation(libs.findLibrary("flyway-core").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.flyway.FlywayApplication"
}
