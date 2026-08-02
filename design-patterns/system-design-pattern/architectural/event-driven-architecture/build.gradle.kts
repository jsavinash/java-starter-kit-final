// ============================================================================
// Event-Driven Architecture Pattern - Build Configuration
// ============================================================================
// Uses the centralized version catalog (gradle/libs.versions.toml) for all
// dependency versions. Java 25 toolchain as defined in .sdkmanrc.
// ============================================================================

plugins {
    `java-library`
    application
}

group = "com.javastarterkit.patterns"
version = "1.0.0-SNAPSHOT"

// Access the version catalog programmatically — the `libs` accessor is
// not always generated for the root build script of an included build.
val libs = rootProject.extensions
    .getByType<org.gradle.api.artifacts.VersionCatalogsExtension>()
    .named("libs")

java {
    sourceCompatibility = JavaVersion.toVersion(libs.findVersion("java-language").get().displayName)
    targetCompatibility = JavaVersion.toVersion(libs.findVersion("java-language").get().displayName)
}

dependencies {
    // SLF4J API for logging abstraction
    implementation(libs.findLibrary("slf4j-api").get())

    // Logback for concrete logging implementation
    runtimeOnly(libs.findLibrary("logback-classic").get())

    // Testing - JUnit BOM as platform, then specific modules
    testImplementation(platform(libs.findLibrary("junit.bom").get()))
    testImplementation(libs.findLibrary("junit.jupiter").get())
    testImplementation(libs.findLibrary("assertj.core").get())
    testImplementation(libs.findLibrary("mockito.core").get())
    testImplementation(libs.findLibrary("mockito.junit.jupiter").get())
    testRuntimeOnly(libs.findLibrary("junit.platform.launcher").get())
}

application {
    mainClass.set("com.javastarterkit.patterns.eventdrivenarchitecture.Main")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:unchecked")
    options.compilerArgs.add("-Xlint:deprecation")
    options.compilerArgs.add("-parameters")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
    // Java 25+ JVM compatibility settings
    jvmArgs(
        "-XX:+EnableDynamicAgentLoading",
        "-Xshare:off"
    )
}