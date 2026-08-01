// ============================================================================
// System Design Patterns - Root Build Configuration
// ============================================================================

plugins {
    java
}

group = "com.javastarterkit.patterns"
version = "1.0.0-SNAPSHOT"

allprojects {
    group = "com.javastarterkit.patterns"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    // Access the version catalog programmatically — the `libs` accessor is
    // not always generated for the root build script of an included build.
    // VersionCatalogsExtension is registered on the root project, not subprojects.
    val libs = rootProject.extensions
        .getByType<org.gradle.api.artifacts.VersionCatalogsExtension>()
        .named("libs")

    java {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    dependencies {
        testImplementation(platform(libs.findLibrary("junit.bom").get()))
        testImplementation(libs.findLibrary("junit.jupiter").get())
        testRuntimeOnly(libs.findLibrary("junit.platform.launcher").get())
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-Xlint:unchecked")
        options.compilerArgs.add("-Xlint:deprecation")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}