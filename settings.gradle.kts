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
        // Explicitly declare plugins used by convention plugins
        // so they are resolvable when applied from composite builds
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

// Platforms BOM composite build
includeBuild("platforms")

// Convention plugins composite build
includeBuild("build-logic")

// ---------------------------------------------------------------------------
// ====== FUNDAMENTALS CATEGORY ======
// Core Spring Boot concepts: IoC, auto-configuration, web apps, REST, etc.
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
include("apps:fundamentals:spring-framework-loaded")
include("apps:fundamentals:spring-core")

// ---------------------------------------------------------------------------
// ====== DATA CATEGORY ======
// Spring Data ecosystem: JPA, MongoDB, Redis, Cassandra, Elasticsearch, etc.
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
include("apps:data:spring-data-bom")
include("apps:data:spring-data-dev-tools")
include("apps:data:spring-data-samples")
include("apps:data:spring-boot-data-geode")
include("apps:data:spring-flyway")
include("apps:data:spring-caching")
include("apps:data:spring-transaction")

// ---------------------------------------------------------------------------
// ====== SECURITY CATEGORY ======
// Authentication, authorization, session management, credentials, etc.
// ---------------------------------------------------------------------------
include("apps:security:spring-security")
include("apps:security:spring-security-kerberos")
include("apps:security:spring-ldap")
include("apps:security:spring-authorization-server")
include("apps:security:spring-session")
include("apps:security:spring-session-data-geode")
include("apps:security:spring-vault")
include("apps:security:spring-credhub")

// ---------------------------------------------------------------------------
// ====== WEB CATEGORY ======
// Web layer: reactive, SOAP, GraphQL, gRPC, HATEOAS, web flow, etc.
// ---------------------------------------------------------------------------
include("apps:web:spring-web-services")
include("apps:web:spring-webflux")
include("apps:web:spring-web-flow")
include("apps:web:spring-graphql")
include("apps:web:spring-grpc")
include("apps:web:spring-hateoas")
include("apps:web:spring-websocket")
include("apps:web:spring-mvc")
include("apps:web:spring-thymeleaf")

// ---------------------------------------------------------------------------
// ====== BATCH & INTEGRATION CATEGORY ======
// Batch processing, messaging integration, enterprise integration patterns.
// ---------------------------------------------------------------------------
include("apps:batch-integration:spring-batch")
include("apps:batch-integration:spring-batch-extensions")
include("apps:batch-integration:spring-integration")
include("apps:batch-integration:spring-integration-flow")

// ---------------------------------------------------------------------------
// ====== CLOUD CATEGORY ======
// Spring Cloud ecosystem: configuration, discovery, gateways, circuit breakers, etc.
// ---------------------------------------------------------------------------
include("apps:cloud:spring-cloud-gateway")
include("apps:cloud:spring-cloud-config")
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
// ====== MESSAGING CATEGORY ======
// Message-driven architectures: AMQP, Kafka, Pulsar, Cloud Stream.
// ---------------------------------------------------------------------------
include("apps:messaging:spring-amqp")
include("apps:messaging:spring-kafka")
include("apps:messaging:spring-pulsar")
include("apps:messaging:spring-cloud-stream")
include("apps:messaging:spring-jms")
include("apps:messaging:spring-mail")

// ---------------------------------------------------------------------------
// ====== EXTENSIONS CATEGORY ======
// Additional Spring ecosystem extensions: Shell, AI, Plugin, Modulith, Guice.
// ---------------------------------------------------------------------------
include("apps:extensions:spring-ai")
include("apps:extensions:spring-shell")
include("apps:extensions:spring-plugin")
include("apps:extensions:spring-modulith")
include("apps:extensions:spring-guice")
include("apps:extensions:spring-retry")

// ---------------------------------------------------------------------------
// ====== TESTING CATEGORY ======
// Testing tools and documentation: REST Docs, etc.
// ---------------------------------------------------------------------------
include("apps:testing:spring-restdocs")
include("apps:testing:spring-testcontainers")
include("apps:testing:spring-mockmvc")
