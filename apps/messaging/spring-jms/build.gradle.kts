// ============================================================================
// Spring JMS — Example Application
// ============================================================================
// Demonstrates Spring JMS concepts:
// - JMS message producer and consumer
// - Message listener containers
// - JMS template for sending messages
// - Integration with ActiveMQ Artemis
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring JMS Example"

dependencies {
    // JMS starter
    implementation(libs.findLibrary("spring-boot-starter-artemis").get())

    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.jms.JmsApplication"
}