package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import com.microsoft.applicationinsights.TelemetryClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
internal class TelemetryServiceTest {
  @Mock
  lateinit var telemetryClient: TelemetryClient

  @InjectMocks
  lateinit var telemetryService: TelemetryService

  @Test
  fun `Put preferences should store preferences and return DTO`() {

    telemetryService.trackEvent(
      TelemetryEventType.PREFERENCES_UPDATED,
      "user_id",
      "preferenceName",
      listOf("B10JQ", "B14AV"),
      listOf("ABC", "DEF")
    )

    var properties = mapOf("userId" to "user_id", "preferenceName" to "preferenceName", "values" to "B10JQ, B14AV", "previousValues" to "ABC, DEF")

    verify(telemetryClient).trackEvent("UserPreferencesUpdated", properties, emptyMap())
  }
}
