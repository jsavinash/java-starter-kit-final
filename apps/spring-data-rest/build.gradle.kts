// ============================================================================
// Spring Data REST — Example Application
// ============================================================================
// Demonstrates Spring Data REST concepts:
// - Automatic REST API exposure
// - Repository REST endpoints
// - HATEOAS hypermedia links
// - Query method exposure
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data REST Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data REST
    implementation(libs.findLibrary("spring-boot-starter-data-rest").get())

    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.datarest.DataRestApplication"
}