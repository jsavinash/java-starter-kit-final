// ============================================================================
// Platforms - Multi-project monorepo
// Contains all sub-platforms as included sub-projects.
// Build conventions are defined in buildSrc/
// ============================================================================

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "platforms"

// ---------------------------------------------------------------------------
// Platform sub-projects
// Each sub-directory under platforms/ is a standalone buildable module.
// ---------------------------------------------------------------------------
include(
    "core",
    "cli",
    "web-api",
    "web-ui",
    "android",
    "ios",
    "mac",
    "linux",
    "windows",
    "microservice",
    "serverless",
    "docker",
    "gradle-plugin",
    "ide-plugin"
)

