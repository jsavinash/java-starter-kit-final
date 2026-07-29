// ============================================================================
// Spring Security Kerberos — Example Application
// ============================================================================
// Demonstrates Spring Security Kerberos concepts:
// - Kerberos protocol integration
// - SPNEGO authentication
// - Single sign-on (SSO)
// - Active Directory integration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Security Kerberos Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Security
    implementation(libs.findLibrary("spring-boot-starter-security").get())

    // Spring Security Kerberos
    implementation(libs.findLibrary("spring-security-kerberos").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.kerberos.KerberosApplication"
}
