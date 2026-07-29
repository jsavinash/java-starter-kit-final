// ============================================================================
// Spring AMQP — Example Application
// ============================================================================
// Demonstrates Spring AMQP concepts:
// - RabbitMQ message publishing and consuming
// - Queue and exchange configuration
// - Message listeners
// - RabbitTemplate usage
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring AMQP Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring AMQP (RabbitMQ)
    implementation(libs.findLibrary("spring-boot-starter-amqp").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.amqp.AmqpApplication"
}
