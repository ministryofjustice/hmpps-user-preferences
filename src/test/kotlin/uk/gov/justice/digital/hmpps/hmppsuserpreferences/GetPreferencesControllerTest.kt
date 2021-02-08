package uk.gov.justice.digital.hmpps.hmppsuserpreferences

import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.integration.IntegrationTestBase

class GetPreferencesControllerTest : IntegrationTestBase() {

  @Test
  fun `GET courts endpoint`() {
    val equalTo = webTestClient.get().uri("/users/USERNAME/preferences/courts")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("courts").isArray
      .jsonPath("courts[0]").isEqualTo("B10JQ")
//      .assertThat(Array).isEqualTo("['B10JQ', 'B62DC', 'B10BD']")
//      .assertTrue(Arrays.equals(['B10JQ', 'B62DC', 'B10BD'], ['B10JQ', 'B62DC', 'B10BD']))
  }

//  @Test
//  fun `GET courts endpoint retrieve a list of courts`() {
//    endpoint = buildEndpoint(setOf("['B10JQ', 'B62DC', 'B10BD']"), true, 3)
//    webTestClient.get().uri("/users/USERNAME/preferences/courts")
//      .exchange()
//      .expectStatus().isOk
//      .expectBody()
//      .jsonPath("courts").isArray()
////      .jsonPath("courts")
////      .mapOf("['B10JQ', 'B62DC', 'B10BD']" to "['B10JQ', 'B62DC', 'B10BD']")
//  }
//
//  @Test
//  fun `retrieve a list of courts`() {
//    webTestClient.get()
//      .uri(String.format(OFFENDER_MATCHES_DETAIL_PATH, COURT_CODE, CASE_NO))
//      .accept(APPLICATION_JSON)
//      .exchange()
//      .expectStatus().isOk()
//      .expectBody()
////      .jsonPath("offenderMatchDetails[0].forename").isEqualTo("Christopher")
////      .jsonPath("courts[0]").isEqualTo("B10JQ")
////      .jsonPath("courts[1]").isEqualTo("B10JQ")
//      .jsonPath("courts").isArray()
//  }

  @Test
  fun whenGetCourts_thenReturnListSorted() {
    given()
      .auth()
      .oauth2(getToken())
      .`when`()
      .get("/courts")
      .then()
      .assertThat()
      .statusCode(200)
      .body("courts", hasSize(3))
      .body("courts[0].code", equalTo("B63AD"))
      .body("courts[0].name", equalTo("Aberystwyth"))
      .body("courts[1].code", equalTo("B33HU"))
      .body("courts[1].name", equalTo("Leicester"))
      .body("courts[2].code", equalTo("B10JQ"))
      .body("courts[2].name", equalTo("North Shields"))
  }
}