plugins {
    id("com.javastarterkit.buildlogic.spring-boot-library")
}

description = "Greeting Service Library"

// Disable annotation processor added by convention plugin
configurations.named("annotationProcessor")
    .configure {
        dependencies.clear()
    }
