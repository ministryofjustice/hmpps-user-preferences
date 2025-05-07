import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "8.1.0"
  kotlin("plugin.spring") version "2.0.20"
  kotlin("jvm") version "2.0.20"
  id("org.jetbrains.kotlin.plugin.jpa") version "1.8.0"
  id("org.flywaydb.flyway") version "9.14.0"
}
val pactVersion = "4.3.16"

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

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
  // security
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

  // database
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql:42.5.2")
  testRuntimeOnly("com.h2database:h2:1.4.200")

  implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
  implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
  implementation("com.microsoft.azure:applicationinsights-web:3.5.4")
  implementation("org.apache.commons:commons-lang3:3.14.0")

  // Test
  testImplementation("au.com.dius.pact.provider:junit5spring:$pactVersion")
  testImplementation("au.com.dius.pact.provider:junit5:$pactVersion")
  testImplementation("au.com.dius.pact:consumer:$pactVersion")
  testImplementation("au.com.dius.pact.consumer:junit5:$pactVersion")
}

tasks.withType<KotlinCompile> {
  compilerOptions {
    jvmTarget = JvmTarget.JVM_21
  }
}
