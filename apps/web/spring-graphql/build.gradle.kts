// ============================================================================
// Spring for GraphQL — Example Application
// ============================================================================
// Demonstrates Spring GraphQL concepts:
// - GraphQL schema definition
// - Query and Mutation resolvers
// - @SchemaMapping and @QueryMapping
// - Data fetchers
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring GraphQL Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring GraphQL
    implementation(libs.findLibrary("spring-boot-starter-graphql").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.graphql.GraphQlApplication"
}
