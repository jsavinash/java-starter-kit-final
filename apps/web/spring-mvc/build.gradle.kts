// ============================================================================
// Spring MVC — Example Application
// ============================================================================
// Demonstrates Spring MVC core concepts:
// - Controller annotations (@Controller, @RestController)
// - Request mapping and parameter binding
// - Content negotiation
// - Exception handling and validation
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring MVC Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.mvc.MvcApplication"
}
