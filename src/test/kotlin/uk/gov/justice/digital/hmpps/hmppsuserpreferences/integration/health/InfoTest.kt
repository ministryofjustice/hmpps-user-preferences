package uk.gov.justice.digital.hmpps.hmppsuserpreferences.integration.health

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.integration.IntegrationTestBase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InfoTest : IntegrationTestBase() {

  @Test
  fun `Info page is accessible`() {
    webTestClient.get()
      .uri("/info")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("app.name").isEqualTo("Hmpps User Preferences")
  }

  @Test
  fun `Info page reports version`() {
    webTestClient.get().uri("/info")
      .exchange()
      .expectStatus().isOk
      .expectBody().jsonPath("build.version").value<String> {
        assertThat(it).startsWith(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
      }
  }

  @Test
  fun `GET courts endpoint`() {
    webTestClient.get().uri("/users/USERNAME/preferences/courts")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("courts").isEqualTo("B10JQ")
  }
}
