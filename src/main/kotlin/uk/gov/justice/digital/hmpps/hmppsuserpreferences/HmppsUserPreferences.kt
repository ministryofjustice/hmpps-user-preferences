package uk.gov.justice.digital.hmpps.hmppsuserpreferences

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication()
@EnableTransactionManagement
class HmppsUserPreferences

fun main(args: Array<String>) {
  runApplication<HmppsUserPreferences>(*args)
}
