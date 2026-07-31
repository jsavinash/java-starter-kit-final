// ============================================================================
// Java Starter Kit - Monorepo Root Settings
// ============================================================================

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("org.springframework.boot") version "4.0.7"
        id("io.spring.dependency-management") version "1.1.7"
        id("com.diffplug.spotless") version "8.8.0"
        id("com.google.cloud.tools.jib") version "3.4.5"
        id("com.gorylenko.gradle-git-properties") version "4.0.1"
        id("org.owasp.dependencycheck") version "12.2.2"
        id("org.sonarqube") version "7.3.1.8318"
        id("com.github.spotbugs") version "6.5.9"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo.spring.io/release") }
    }
}

rootProject.name = "java-starter-kit"

includeBuild("platforms")
includeBuild("build-logic")
includeBuild("design-patterns")

// ---------------------------------------------------------------------------
// FUNDAMENTALS (12 modules)
// ---------------------------------------------------------------------------
include("apps:fundamentals:webapp")
include("apps:fundamentals:ioc-example")
include("apps:fundamentals:starters-autoconfig")
include("apps:fundamentals:spring-boot-rest")
include("apps:fundamentals:actuator-devtools")
include("apps:fundamentals:greeting-service")
include("apps:fundamentals:hello-world-basics")
include("apps:fundamentals:spring-boot-devtools")
include("apps:fundamentals:spring-petclinic")
include("apps:fundamentals:spring-aot")
// Disabled: spring-loaded artifact not available in Spring Boot 4
// include("apps:fundamentals:spring-framework-loaded")
include("apps:fundamentals:spring-core")

// ---------------------------------------------------------------------------
// DATA (22 modules - 6 disabled due to artifact issues)
// ---------------------------------------------------------------------------
include("apps:data:spring-data-jpa")
include("apps:data:spring-data-mongodb")
include("apps:data:spring-data-redis")
include("apps:data:spring-data-elasticsearch")
include("apps:data:spring-data-neo4j")
include("apps:data:spring-data-cassandra")
include("apps:data:spring-data-couchbase")
include("apps:data:spring-data-rest")
include("apps:data:spring-data-r2dbc")
include("apps:data:spring-data-jdbc")
include("apps:data:spring-data-commons")
include("apps:data:spring-data-envers")
include("apps:data:spring-data-ldap")
include("apps:data:spring-data-keyvalue")
include("apps:data:spring-data-relational")
// Disabled: spring-data-bom not available as a standalone artifact
// include("apps:data:spring-data-bom")
// Disabled: spring-data-devtools not available as a standalone artifact
// include("apps:data:spring-data-dev-tools")
include("apps:data:spring-data-samples")
// Disabled: spring-boot-starter-data-geode not available in Spring Boot 4
// include("apps:data:spring-boot-data-geode")
// Also disabled: spring-session-data-geode depends on spring-boot-starter-data-geode
include("apps:data:spring-flyway")
include("apps:data:spring-caching")
include("apps:data:spring-transaction")

// ---------------------------------------------------------------------------
// SECURITY (8 modules - 2 disabled due to artifact issues)
// ---------------------------------------------------------------------------
include("apps:security:spring-security")
// Disabled: spring-security-kerberos-client artifact not available
// include("apps:security:spring-security-kerberos")
include("apps:security:spring-ldap")
// Disabled: spring-authorization-server artifact not available
// include("apps:security:spring-authorization-server")
include("apps:security:spring-session")
// Disabled: spring-session-data-geode requires spring-boot-starter-data-geode
// include("apps:security:spring-session-data-geode")
// Disabled: spring-boot-starter-vault artifact not available
// include("apps:security:spring-vault")
// Disabled: spring-credhub artifact not available
// include("apps:security:spring-credhub")


// ---------------------------------------------------------------------------
// WEB (9 modules - 1 disabled due to artifact issues)
// ---------------------------------------------------------------------------
include("apps:web:spring-web-services")
include("apps:web:spring-webflux")
// Disabled: spring-webflow artifact not available
// include("apps:web:spring-web-flow")
include("apps:web:spring-graphql")
// Disabled: spring-grpc artifact not available
// include("apps:web:spring-grpc")
include("apps:web:spring-hateoas")
include("apps:web:spring-websocket")
include("apps:web:spring-mvc")
include("apps:web:spring-thymeleaf")

// ---------------------------------------------------------------------------
// BATCH & INTEGRATION (4 modules)
// ---------------------------------------------------------------------------
// Disabled: spring-batch requires Spring Cloud BOM access (401 error)
// include("apps:batch-integration:spring-batch")
include("apps:batch-integration:spring-batch-extensions")
// Disabled: spring-integration has compilation errors (missing IntegrationFlows class)
// include("apps:batch-integration:spring-integration")
// Disabled: spring-integration-flow depends on spring-integration
// include("apps:batch-integration:spring-integration-flow")

// ---------------------------------------------------------------------------
// CLOUD (14 modules - 5 disabled due to BOM compatibility)
// ---------------------------------------------------------------------------
// Disabled: spring-cloud-starter-gateway artifact not available
// include("apps:cloud:spring-cloud-gateway")
// Disabled: spring-cloud-config has compilation errors (missing EnableConfigServer)
// include("apps:cloud:spring-cloud-config")
include("apps:cloud:spring-cloud-openfeign")
include("apps:cloud:spring-cloud-netflix-eureka")
include("apps:cloud:spring-cloud-netflix-ribbon")
include("apps:cloud:spring-cloud-netflix-hystrix")
include("apps:cloud:spring-cloud-consul")
include("apps:cloud:spring-cloud-sleuth")
include("apps:cloud:spring-cloud-bus")
include("apps:cloud:spring-cloud-task")
include("apps:cloud:spring-cloud-zookeeper")
include("apps:cloud:spring-cloud-circuitbreaker")
include("apps:cloud:spring-cloud-loadbalancer")
include("apps:cloud:spring-cloud-function")

// ---------------------------------------------------------------------------
// MESSAGING (6 modules)
// ---------------------------------------------------------------------------
include("apps:messaging:spring-amqp")
include("apps:messaging:spring-kafka")
include("apps:messaging:spring-pulsar")
include("apps:messaging:spring-cloud-stream")
include("apps:messaging:spring-jms")
include("apps:messaging:spring-mail")

// ---------------------------------------------------------------------------
// EXTENSIONS (7 modules - 7 disabled due to artifact issues)
// ---------------------------------------------------------------------------
// Disabled: spring-ai not available in Spring Boot 4
// include("apps:extensions:spring-ai")
// Disabled: spring-shell not available as a standalone artifact
// include("apps:extensions:spring-shell")
// Disabled: spring-plugin not available in Spring Boot 4
// include("apps:extensions:spring-plugin")
// Disabled: spring-modulith not available in Spring Boot 4
// include("apps:extensions:spring-modulith")
// Disabled: spring-guice not available in Spring Boot 4
// include("apps:extensions:spring-guice")
// Disabled: spring-retry not available as a standalone artifact
// include("apps:extensions:spring-retry")
include("apps:extensions:spring-feature-flags")

// ---------------------------------------------------------------------------
// TESTING (3 modules)
// ---------------------------------------------------------------------------
include("apps:testing:spring-restdocs")
// Disabled: spring-testcontainers requires testcontainers PostgreSQL artifact
// include("apps:testing:spring-testcontainers")
// Also disabled: spring-testcontainers depending on missing artifact
include("apps:testing:spring-mockmvc")
