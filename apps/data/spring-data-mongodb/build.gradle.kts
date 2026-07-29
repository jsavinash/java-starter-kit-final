// ============================================================================
// Spring Data MongoDB — Example Application
// ============================================================================
// Demonstrates Spring Data MongoDB concepts:
// - Document-based data modeling
// - MongoDB repository pattern
// - Reactive and imperative data access
// - Embedded and referenced documents
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data MongoDB Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data MongoDB
    implementation(libs.findLibrary("spring-boot-starter-data-mongodb").get())

    // Reactive MongoDB
    implementation(libs.findLibrary("spring-boot-starter-data-mongodb-reactive").get())

    // Validation
    implementation(libs.findLibrary("spring-boot-starter-validation").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
    testImplementation(libs.findLibrary("flapdoodle-embed-mongo").get())
}

springBoot {
    mainClass = "com.javastarterkit.mongodb.MongoDbApplication"
}
