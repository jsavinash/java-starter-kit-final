// ============================================================================
// Spring Data Envers — Example Application
// ============================================================================
// Demonstrates Spring Data Envers concepts:
// - Entity audit logging
// - Revision history tracking
// - @Audited annotation for entity auditing
// - Historical data queries
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Data Envers Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data JPA with Envers
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Envers for auditing
    implementation(libs.findLibrary("hibernate-envers").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.envers.EnversApplication"
}
