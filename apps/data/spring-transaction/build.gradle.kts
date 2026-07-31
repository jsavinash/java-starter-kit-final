// ============================================================================
// Spring Transaction — Example Application
// ============================================================================
// Demonstrates Spring Transaction Management concepts:
// - @Transactional annotation propagation
// - Programmatic transaction management
// - Rollback rules and isolation levels
// - Distributed transactions (XA)
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Transaction Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.transaction.TransactionApplication"
}
