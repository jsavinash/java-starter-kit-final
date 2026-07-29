// ============================================================================
// Spring Thymeleaf — Example Application
// ============================================================================
// Demonstrates Thymeleaf templating concepts:
// - Server-side HTML rendering
// - Template fragments and layouts
// - Form binding and validation
// - Internationalization (i18n)
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Thymeleaf Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Thymeleaf starter
    implementation(libs.findLibrary("spring-boot-starter-thymeleaf").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.thymeleaf.ThymeleafApplication"
}
