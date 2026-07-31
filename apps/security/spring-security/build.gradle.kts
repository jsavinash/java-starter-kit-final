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

description = "Spring Security Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Security
    implementation(libs.findLibrary("spring-boot-starter-security").get())

    // JWT support (jjwt - JSON Web Token)
    implementation(libs.findLibrary("jjwt-api").get())
    implementation(libs.findLibrary("jjwt-impl").get())
    implementation(libs.findLibrary("jjwt-jackson").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.security.SecurityApplication"
}
