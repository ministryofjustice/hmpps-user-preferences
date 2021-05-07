plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "3.1.7"
  kotlin("plugin.spring") version "1.5.0"
  id("org.jetbrains.kotlin.plugin.jpa") version "1.5.0"
  id("org.flywaydb.flyway") version "7.8.2"
}
val pactVersion = "4.2.2"

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
  implementation("org.hibernate:hibernate-core:5.4.31.Final")
  implementation("com.vladmihalcea:hibernate-types-52:2.10.4")
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("com.h2database:h2:1.4.200")

  implementation("io.springfox:springfox-boot-starter:3.0.0")
  testImplementation("au.com.dius.pact.provider:junit5spring:$pactVersion")

  testImplementation("au.com.dius:pact-jvm-provider-junit5:4.0.10")
}
