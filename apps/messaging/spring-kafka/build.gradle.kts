// ============================================================================
// Spring for Apache Kafka — Example Application
// ============================================================================
// Demonstrates Spring Kafka concepts:
// - Producer and Consumer configuration
// - Message-driven architecture
// - Kafka templates and listeners
// - Error handling
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Kafka Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Kafka
    implementation(libs.findLibrary("spring-kafka").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
    testImplementation(libs.findLibrary("spring-kafka-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.kafka.KafkaApplication"
}
