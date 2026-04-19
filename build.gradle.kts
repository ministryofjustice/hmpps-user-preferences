plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "10.2.1"
  kotlin("plugin.spring") version "2.3.20"
  kotlin("jvm") version "2.3.20"
  kotlin("plugin.jpa") version "2.3.20"
  id("org.flywaydb.flyway") version "11.20.3"
}

dependencies {
  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:2.1.0")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-webclient")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

  // database
  implementation("org.springframework.boot:spring-boot-starter-flyway:4.0.5")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.flywaydb:flyway-database-postgresql:11.20.3")
  runtimeOnly("org.postgresql:postgresql:42.7.10")
  testRuntimeOnly("com.h2database:h2:2.4.240")

  implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
  implementation("com.microsoft.azure:applicationinsights-web:3.7.8")
  implementation("com.microsoft.azure:applicationinsights-logging-logback:2.6.4")
  implementation("org.apache.commons:commons-lang3:3.20.0")

  // Test
  testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
  testImplementation("org.springframework.boot:spring-boot-data-jpa-test")
  testImplementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter-test:2.1.0")
  testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
  testImplementation("org.wiremock:wiremock-standalone:3.13.2")
  testImplementation("au.com.dius.pact.provider:junit5spring:4.6.20")
  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.40") {
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
    force("org.apache.tomcat.embed:tomcat-embed-core:11.0.21")
  }
}

kotlin {
  jvmToolchain(25)
  compilerOptions {
    freeCompilerArgs.addAll("-Xannotation-default-target=param-property")
  }
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
