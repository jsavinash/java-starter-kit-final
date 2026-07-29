// ============================================================================
// Spring AI — Example Application
// ============================================================================
// Demonstrates Spring AI concepts:
// - AI/ML integration patterns
// - Vector databases and embeddings
// - Chat client abstractions
// - Prompt engineering
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring AI Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring AI
    implementation(libs.findLibrary("spring-ai-core").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.ai.AiApplication"
}
