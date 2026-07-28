// ============================================================================
// Spring Data Cassandra — Example Application
// ============================================================================
// Demonstrates Spring Data Cassandra concepts:
// - Cassandra database operations
// - Partition keys and clustering keys
// - Cassandra repositories
// - CQL queries
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data Cassandra Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data Cassandra
    implementation(libs.findLibrary("spring-boot-starter-data-cassandra").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.cassandra.CassandraApplication"
}