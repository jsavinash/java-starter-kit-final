// ============================================================================
// Spring Data Elasticsearch — Example Application
// ============================================================================
// Demonstrates Spring Data Elasticsearch concepts:
// - Document indexing and search
// - Elasticsearch repositories
// - Full-text search
// - Query DSL
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data Elasticsearch Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data Elasticsearch
    implementation(libs.findLibrary("spring-boot-starter-data-elasticsearch").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.elasticsearch.ElasticsearchApplication"
}