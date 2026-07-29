// ============================================================================
// Spring REST Docs — Example Application
// ============================================================================
// Demonstrates Spring REST Docs concepts:
// - API documentation generation
// - Test-driven documentation with @AutoConfigureRestDocs
// - Snippets generation
// - Asciidoctor integration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring REST Docs Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring REST Docs
    implementation(libs.findLibrary("spring-restdocs-core").get())
    implementation(libs.findLibrary("spring-restdocs-mockmvc").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
    testImplementation(libs.findLibrary("spring-restdocs-core").get())
}

springBoot {
    mainClass = "com.javastarterkit.restdocs.RestDocsApplication"
}
