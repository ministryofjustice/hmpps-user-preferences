plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "4.1.4"
  kotlin("plugin.spring") version "1.6.20"
  id("org.jetbrains.kotlin.plugin.jpa") version "1.6.20"
  id("org.flywaydb.flyway") version "8.5.7"
}
val pactVersion = "4.3.6"

tasks {
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

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  // security
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

  // database
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.hibernate:hibernate-core:5.6.7.Final")
  implementation("com.vladmihalcea:hibernate-types-52:2.16.0")
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql")
  testRuntimeOnly("com.h2database:h2:1.4.200")

  implementation("org.springdoc:springdoc-openapi-ui:1.6.7")

  // Test
  testImplementation("au.com.dius.pact.provider:junit5spring:$pactVersion")
  testImplementation("au.com.dius.pact.provider:junit5:$pactVersion")
  testImplementation("au.com.dius.pact:consumer:$pactVersion")
  testImplementation("au.com.dius.pact.consumer:junit5:$pactVersion")
}
