// ============================================================================
// Spring Core — Example Application
// ============================================================================
// Demonstrates Spring Framework Core concepts:
// - Spring IoC container (BeanFactory, ApplicationContext)
// - Dependency injection patterns
// - Bean lifecycle and scopes
// - Resource handling and SpEL expressions
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Core Example"

dependencies {
    // Core starter
    implementation(libs.findLibrary("spring-boot-starter").get())

    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.core.CoreApplication"
}