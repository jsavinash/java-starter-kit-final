// ============================================================================
// Serverless Platform - AWS Lambda functions
// ============================================================================

plugins {
    id("platforms.java-conventions")
    `java-library`
    alias(libs.plugins.shadow)
}

description = "Serverless platform (AWS Lambda)"

dependencies {
    implementation(project(":core"))

    // AWS Lambda
    implementation(libs.aws.lambda.core)
    implementation(libs.aws.lambda.events)

    // JSON
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)

    // Logging
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.assertj.core)
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("serverless")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}