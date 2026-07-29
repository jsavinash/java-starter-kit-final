// ============================================================================
// Spring Authorization Server — Example Application
// ============================================================================
// Demonstrates Spring Authorization Server concepts:
// - OAuth 2.0 Authorization Server
// - Client registration
// - JWT tokens
// - Authorization endpoints
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Authorization Server Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Authorization Server
    implementation(libs.findLibrary("spring-authorization-server").get())

    // Spring Security
    implementation(libs.findLibrary("spring-boot-starter-security").get())

    // JWT support
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.authserver.AuthorizationServerApplication"
}