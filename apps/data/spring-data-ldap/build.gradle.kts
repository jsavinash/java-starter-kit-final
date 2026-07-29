// ============================================================================
// Spring Data LDAP — Example Application
// ============================================================================
// Demonstrates Spring Data LDAP concepts:
// - LDAP repository abstraction
// - CRUD operations on directory entries
// - @Entry annotation for mapping
// - Query methods for directory searches
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data LDAP Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data LDAP
    implementation(libs.findLibrary("spring-boot-starter-data-ldap").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.ldap.repo.LdapRepositoryApplication"
}
