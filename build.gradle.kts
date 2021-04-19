plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "3.1.6"
  kotlin("plugin.spring") version "1.4.32"
  id("org.jetbrains.kotlin.plugin.jpa") version "1.4.32"
  id("org.flywaydb.flyway") version "7.8.1"
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
  implementation("org.hibernate:hibernate-core:5.4.30.Final")
  implementation("com.vladmihalcea:hibernate-types-52:2.10.4")
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("com.h2database:h2:1.4.200")

  implementation("io.springfox:springfox-boot-starter:3.0.0")
}
