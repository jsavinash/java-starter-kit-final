// ============================================================================
// Spring Data Neo4j — Example Application
// ============================================================================
// Demonstrates Spring Data Neo4j concepts:
// - Graph database operations
// - Node and relationship mapping
// - Cypher queries
// - Neo4j repositories
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data Neo4j Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data Neo4j
    implementation(libs.findLibrary("spring-boot-starter-data-neo4j").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.neo4j.Neo4jApplication"
}