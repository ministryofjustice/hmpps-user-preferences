package uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller

import com.google.common.net.HttpHeaders
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.boot.test.mock.mockito.MockBean
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.TEST_TOKEN
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.integration.IntegrationTestBase
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.service.PreferencesService

class GetPreferencesControllerTest : IntegrationTestBase() {
  private val courtKey: String = "courts"
  private val userId: String = "userid"
  private val courts = listOf("B10JQ", "C20RE")

  private val prefsPath: String = "/users/%s/preferences/%s"

  @MockBean
  lateinit var preferencesService: PreferencesService

  var inputPreferences = PreferencesDTO(courts)

  val outputPreferences = PreferencesDTO(courts)

  @Test
  fun `GET preferences endpoint returns existing preferences`() {

    // TODO: Remove these mocks and have it call the actual service

    whenever(preferencesService.getPreferences(userId, courtKey)).thenReturn(outputPreferences)

    webTestClient.get().uri(String.format(prefsPath, userId, courtKey))
      .header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$.items.size()").isEqualTo(2)
      .jsonPath("items[0]").isEqualTo("B10JQ")
      .jsonPath("items[1]").isEqualTo("C20RE")

    verify(preferencesService).getPreferences(userId, courtKey)
    verifyNoMoreInteractions(preferencesService)
  }

  @Test
  fun `PUT preferences endpoint creates preferences`() {

    // TODO: Remove these mocks and have it call the actual service
    whenever(preferencesService.putPreferences(userId, courtKey, inputPreferences)).thenReturn(outputPreferences)

    webTestClient.put().uri(String.format(prefsPath, userId, courtKey))
      .bodyValue(inputPreferences)
      .header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
      .exchange()
      .expectStatus().isCreated
      .expectBody()
      .jsonPath("$.items.size()").isEqualTo(2)
      .jsonPath("items[0]").isEqualTo("B10JQ")
      .jsonPath("items[1]").isEqualTo("C20RE")

    verify(preferencesService).putPreferences(userId, courtKey, inputPreferences)
    verifyNoMoreInteractions(preferencesService)
  }
}
