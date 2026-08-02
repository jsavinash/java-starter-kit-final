// ============================================================================
// Flux Pattern — Build Configuration
// ============================================================================
// Java 25 | Gradle 9.6.1 | JUnit 5.12 | AssertJ 3.27 | Lombok 1.18.38
// ============================================================================

plugins {
    `java`
    `application`
}

group = "com.javastarterkit.patterns"
version = "1.0.0-SNAPSHOT"

// Access the version catalog programmatically for included-build compatibility
val libs = rootProject.extensions
    .getByType<org.gradle.api.artifacts.VersionCatalogsExtension>()
    .named("libs")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(
            libs.findVersion("java-language").get().displayName
        )
        vendor = org.gradle.jvm.toolchain.JvmVendorSpec.AMAZON
    }
}

application {
    mainClass.set("com.javastarterkit.patterns.flux.Main")
}

dependencies {
    // ── Lombok: boilerplate reduction (constructor / getter generation) ──────
    compileOnly(libs.findLibrary("lombok").get())
    annotationProcessor(libs.findLibrary("lombok").get())

    testCompileOnly(libs.findLibrary("lombok").get())
    testAnnotationProcessor(libs.findLibrary("lombok").get())

    // ── Testing ─────────────────────────────────────────────────────────────
    testImplementation(platform(libs.findLibrary("junit.bom").get()))
    testImplementation(libs.findLibrary("junit.jupiter").get())
    testImplementation(libs.findLibrary("assertj.core").get())
    testRuntimeOnly(libs.findLibrary("junit.platform.launcher").get())
}

// ── Compiler flags ───────────────────────────────────────────────────────────
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:unchecked")
    options.compilerArgs.add("-Xlint:deprecation")
    options.compilerArgs.add("-Xlint:rawtypes")
    options.compilerArgs.add("-parameters")
}

tasks.test {
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