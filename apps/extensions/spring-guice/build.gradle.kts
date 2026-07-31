// ============================================================================
// Spring Guice — Example Application
// ============================================================================
// Demonstrates Spring Guice concepts:
// - Interoperability between Spring and Google Guice
// - Dependency injection with Guice
// - Module configuration
// - Bean bridging between frameworks
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Guice Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Guice
    implementation(libs.findLibrary("spring-guice").get())

    // Google Guice
    implementation(libs.findLibrary("guice").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.guice.GuiceApplication"
}
