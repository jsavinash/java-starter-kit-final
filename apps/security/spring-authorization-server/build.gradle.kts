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

description = "Spring Authorization Server Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Authorization Server
    implementation(libs.findLibrary("spring-authorization-server").get())

    // Spring Security
    implementation(libs.findLibrary("spring-boot-starter-security").get())

    // JWT support
    implementation(libs.findLibrary("jjwt-api").get())
    implementation(libs.findLibrary("jjwt-impl").get())
    implementation(libs.findLibrary("jjwt-jackson").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.authserver.AuthorizationServerApplication"
}
