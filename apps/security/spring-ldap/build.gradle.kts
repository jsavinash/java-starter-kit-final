// ============================================================================
// Spring LDAP — Example Application
// ============================================================================
// Demonstrates Spring LDAP concepts:
// - LDAP authentication
// - Directory operations
// - LDAP template usage
// - User search and binding
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring LDAP Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring LDAP
    implementation(libs.findLibrary("spring-boot-starter-data-ldap").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.ldap.LdapApplication"
}
