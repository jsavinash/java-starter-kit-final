// ============================================================================
// Spring Data Relational — Example Application
// ============================================================================
// Demonstrates Spring Data Relational concepts:
// - Base module for JDBC and R2DBC
// - Relational database abstractions
// - Query derivation for SQL
// - Transaction management
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Data Relational Example"

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
    mainClass = "com.javastarterkit.relational.RelationalApplication"
}
