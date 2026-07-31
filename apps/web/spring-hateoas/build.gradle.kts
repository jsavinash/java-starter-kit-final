// ============================================================================
// Spring HATEOAS — Example Application
// ============================================================================
// Demonstrates Spring HATEOAS concepts:
// - Hypermedia-driven REST APIs
// - EntityModel and CollectionModel
// - Links and relation types
// - Assemblers
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring HATEOAS Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring HATEOAS
    implementation(libs.findLibrary("spring-boot-starter-hateoas").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.hateoas.HateoasApplication"
}
