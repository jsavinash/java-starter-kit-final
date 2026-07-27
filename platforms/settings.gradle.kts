// ============================================================================
// Java Starter Kit - Platforms Composite Build Settings
// ============================================================================

rootProject.name = "platforms"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include("spring-boot")