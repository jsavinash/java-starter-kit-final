// ============================================================================
// Spring Shell — Example Application
// ============================================================================
// Demonstrates Spring Shell concepts:
// - Interactive CLI application
// - Command definitions with @ShellMethod
// - Parameter binding and conversion
// - Shell prompt customization
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Shell Example"

dependencies {
    // Spring Shell
    implementation(libs.findLibrary("spring-shell-starter").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.shell.ShellApplication"
}