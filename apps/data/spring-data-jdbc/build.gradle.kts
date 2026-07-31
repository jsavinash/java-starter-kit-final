// ============================================================================
// Spring Data JDBC — Example Application
// ============================================================================
// Demonstrates Spring Data JDBC concepts:
// - Simple JDBC-based data access
// - Aggregate-oriented persistence
// - CrudRepository and query derivation
// - Database initialization
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Data JDBC Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data JDBC
    implementation(libs.findLibrary("spring-boot-starter-data-jdbc").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.jdbc.JdbcApplication"
}
