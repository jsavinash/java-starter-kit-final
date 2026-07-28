// ============================================================================
// Spring Security — Example Application
// ============================================================================
// Demonstrates Spring Security concepts:
// - Authentication and authorization
// - JWT-based security
// - Method-level security with @PreAuthorize
// - Security configuration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Security Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Security
    implementation(libs.findLibrary("spring-boot-starter-security").get())

    // JWT support (jjwt - JSON Web Token)
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.security.SecurityApplication"
}