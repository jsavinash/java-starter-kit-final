// ============================================================================
// Spring Data JPA — Example Application
// ============================================================================
// Demonstrates Spring Data JPA concepts:
// - JPA Entities and relationships
// - Repository interfaces
// - CRUD operations
// - Transaction management
// ============================================================================

import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Data JPA Example"

dependencies {
    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.datajpa.SpringDataJpaApplication"
}
