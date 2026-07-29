// ============================================================================
// Spring Cloud Zookeeper — Example Application
// ============================================================================
// Demonstrates Spring Cloud Zookeeper concepts:
// - Service discovery with Apache Zookeeper
// - Configuration management
// - Distributed coordination
// - Service registration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Zookeeper Example"

dependencies {
    // Spring Cloud Zookeeper Discovery
    implementation(libs.findLibrary("spring-cloud-starter-zookeeper-discovery").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.zookeeper.ZooKeeperApplication"
}
