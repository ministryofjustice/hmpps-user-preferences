plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "2.1.0"
  kotlin("plugin.spring") version "1.4.21"
  id("org.jetbrains.kotlin.plugin.jpa") version "1.4.21"
}

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  // security
  implementation("org.springframework.boot:spring-boot-starter-webflux")

  // database
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.hibernate:hibernate-core:5.4.24.Final")
  implementation("com.vladmihalcea:hibernate-types-52:2.10.2")
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql")
}
