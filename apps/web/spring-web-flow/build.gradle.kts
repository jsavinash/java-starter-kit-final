// ============================================================================
// Spring Web Flow — Example Application
// ============================================================================
// Demonstrates Spring Web Flow concepts:
// - Flow definition and management
// - View-state and action-state
// - Conversation scope
// - Multi-page workflow orchestration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Web Flow Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Web Flow
    implementation(libs.findLibrary("spring-webflow").get())

    // Thymeleaf for views
    implementation(libs.findLibrary("spring-boot-starter-thymeleaf").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.webflow.WebFlowApplication"
}