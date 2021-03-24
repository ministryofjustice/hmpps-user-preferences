package uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller

import com.google.common.net.HttpHeaders
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.TEST_TOKEN
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.integration.IntegrationTestBase
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.PreferenceRepository

class GetPreferencesControllerIntTest : IntegrationTestBase() {
  private val courtKey: String = "courts"
  private val userId: String = "userid"
  private val courts = listOf("Sheffield", "North Tyneside")

  private val prefsPath: String = "/users/%s/preferences/%s"

  var inputPreferences = PreferencesDTO(courts)

  @Autowired
  lateinit var preferenceRepository: PreferenceRepository

  @Test
  fun `GET preferences endpoint returns existing preferences`() {

    preferenceRepository.save(Preference(userId, courtKey, "Sheffield"))
    preferenceRepository.save(Preference(userId, courtKey, "North Tyneside"))

    webTestClient.get().uri(String.format(prefsPath, userId, courtKey))
      .header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$.items.size()").isEqualTo(2)
      .jsonPath("items[0]").isEqualTo("Sheffield")
      .jsonPath("items[1]").isEqualTo("North Tyneside")
  }

  @Test
  fun `PUT preferences endpoint creates preferences`() {

    webTestClient.put().uri(String.format(prefsPath, userId, courtKey))
      .bodyValue(inputPreferences)
      .header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
      .exchange()
      .expectStatus().isCreated
      .expectBody()
      .jsonPath("$.items.size()").isEqualTo(2)
      .jsonPath("items[0]").isEqualTo("Sheffield")
      .jsonPath("items[1]").isEqualTo("North Tyneside")

    assertThat(preferenceRepository.findByHmppsUserIdAndName(userId, courtKey))
      .contains(
        Preference(userId, courtKey, "Sheffield"),
        Preference(userId, courtKey, "North Tyneside")
      )
  }
}
