package uk.gov.justice.digital.hmpps.hmppsuserpreferences

import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.boot.test.mock.mockito.MockBean
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.integration.IntegrationTestBase
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.service.UsersService
import java.util.*
import java.util.Arrays.asList

class GetPreferencesControllerTest : IntegrationTestBase() {

  private val courtKey: String = "courts"
  private val userId: String = "userid"

  private val prefsPath: String = "/users/%s/preferences/%s"

  @MockBean
  lateinit var usersService: UsersService

  @Test
  fun `GET preferences endpoint`() {

    whenever(usersService.getPreferenceValues(userId, courtKey)).thenReturn(asList("B10JQ", "C20RE"))

    webTestClient.get().uri(String.format(prefsPath, userId, courtKey))
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$.courts.size()").isEqualTo(2)
      .jsonPath("courts[0]").isEqualTo("B10JQ")
      .jsonPath("courts[1]").isEqualTo("C20RE")
  }
}
