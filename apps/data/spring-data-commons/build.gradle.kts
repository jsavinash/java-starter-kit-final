// ============================================================================
// Spring Data Commons — Example Application
// ============================================================================
// Demonstrates Spring Data Commons concepts:
// - Auditing and @CreatedDate/@LastModifiedDate
// - @Version for optimistic locking
// - Common repository abstractions
// - Persistent entities
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data Commons Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data JPA (includes commons)
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.commons.CommonsApplication"
}