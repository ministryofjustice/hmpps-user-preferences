plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "11.0.12"
  kotlin("plugin.spring") version "2.4.21"
  kotlin("jvm") version "2.4.21"
  kotlin("plugin.jpa") version "2.4.21"
  id("org.flywaydb.flyway") version "11.20.3"
}

dependencies {
  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:3.0.3")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-webclient")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.1")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

  // database
  implementation("org.springframework.boot:spring-boot-starter-flyway")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.flywaydb:flyway-database-postgresql")
  runtimeOnly("org.postgresql:postgresql")
  testRuntimeOnly("com.h2database:h2:2.5.252")

  implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
  implementation("com.microsoft.azure:applicationinsights-web:3.7.10")
  implementation("com.microsoft.azure:applicationinsights-logging-logback:2.6.4")
  implementation("org.apache.commons:commons-lang3:3.21.0")

  // Test
  testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
  testImplementation("org.springframework.boot:spring-boot-data-jpa-test")
  testImplementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter-test:3.0.3")
  testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
  testImplementation("org.wiremock:wiremock-standalone:3.13.2")
  testImplementation("au.com.dius.pact.provider:junit5spring:4.7.5")
  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.48") {
    exclude(group = "io.swagger.core.v3")
  }
}

// TODO: review these each time we update a dependency, remove once parent dependencies are patched
configurations.all {
  resolutionStrategy {
    // Force patched version of rhino to fix CVE-2025-66453
    force("org.mozilla:rhino:1.9.1")

    // These patches are due to vulnerable deps used
    // TODO: remove when we next update
    // I patched assertj in hmpps-kotlin-spring-boot-starter:2.0.2, but the static analysis is still
    // picking up the vulnerable version, so I am forcing the patched version here as well
    // Force patched version of assertj to fix CVE-2026-24400 (score 7.3)
    force("org.assertj:assertj-core:3.27.7")
    // Force patched version of tomcat embed to fix CVE-2026-24734 (score 7.5)
    force("org.apache.tomcat.embed:tomcat-embed-core:11.0.26")
  }
}

kotlin {
  jvmToolchain(25)
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
  }

  test {
    useJUnitPlatform {
      exclude("**/*PactTest*")
    }
  }

  register<Test>("pactTestPublish") {
    description = "Run and publish Pact provider tests"
    group = "verification"

    systemProperty("pact.provider.tag", System.getenv("PACT_PROVIDER_TAG"))
    systemProperty("pact.provider.version", System.getenv("PACT_PROVIDER_VERSION"))
    systemProperty("pact.verifier.publishResults", System.getenv("PACT_PUBLISH_RESULTS") ?: "false")

    useJUnitPlatform {
      include("**/*PactTest*")
    }
  }
}
