// ============================================================================
// Spring Data Couchbase — Example Application
// ============================================================================
// Demonstrates Spring Data Couchbase concepts:
// - NoSQL document database operations
// - Reactive and imperative support
// - Couchbase repositories
// - N1QL queries
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data Couchbase Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data Couchbase
    implementation(libs.findLibrary("spring-boot-starter-data-couchbase").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.couchbase.CouchbaseApplication"
}
