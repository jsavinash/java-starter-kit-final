// ============================================================================
// Spring WebSocket — Example Application
// ============================================================================
// Demonstrates WebSocket concepts:
// - STOMP messaging over WebSocket
// - Real-time bidirectional communication
// - Message handling and broadcasting
// - SockJS fallback options
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring WebSocket Example"

dependencies {
    // WebSocket starter
    implementation(libs.findLibrary("spring-boot-starter-websocket").get())

    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.websocket.WebSocketApplication"
}