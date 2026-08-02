plugins {
    java
    application
}

group = "com.javastarterkit.patterns"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.AMAZON
    }
    withSourcesJar()
    withJavadocJar()
}

application {
    mainClass = "com.javastarterkit.patterns.modelviewpresenter.ModelViewPresenter"
}

repositories {
    mavenCentral()
}

dependencies {
    // Testing
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
    testImplementation("org.awaitility:awaitility:4.3.0")

    // Concurrency utilities
    implementation("com.google.guava:guava:33.4.0-jre")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.16")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showExceptions = true
        showStackTraces = true
        showCauses = true
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation", "-parameters"))
}

tasks.named<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
}

tasks.named<JavaCompile>("compileTestJava") {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
        attributes["Implementation-Title"] = "model-view-presenter"
        attributes["Implementation-Version"] = version
    }
}