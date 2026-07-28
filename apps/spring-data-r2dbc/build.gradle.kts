// ============================================================================
// Spring Data R2DBC — Example Application
// ============================================================================
// Demonstrates Spring Data R2DBC concepts:
// - Reactive relational database access
// - R2DBC repositories
// - Reactive CRUD operations
// - Database client configuration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data R2DBC Example"

dependencies {
    // WebFlux starter
    implementation(libs.findLibrary("spring-boot-starter-webflux").get())

    // Spring Data R2DBC
    implementation(libs.findLibrary("spring-boot-starter-data-r2dbc").get())

    // H2 Database (reactive)
    implementation(libs.findLibrary("r2dbc-h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.r2dbc.R2dbcApplication"
}