package uk.gov.justice.digital.hmpps.hmppsuserpreferences

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication()
class HmppsUserPreferences

fun main(args: Array<String>) {
  runApplication<HmppsUserPreferences>(*args)
}
