// ============================================================================
// Spring gRPC — Example Application
// ============================================================================
// Demonstrates Spring gRPC concepts:
// - gRPC service definition and implementation
// - Protocol Buffers for service contracts
// - Streaming RPCs
// - Interceptors and error handling
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring gRPC Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring gRPC
    implementation(libs.findLibrary("spring-grpc").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.grpc.GrpcApplication"
}