package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import com.microsoft.applicationinsights.TelemetryClient
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class TelemetryServiceTest {
  @Mock
  lateinit var telemetryClient: TelemetryClient

  @InjectMocks
  lateinit var telemetryService: TelemetryService

  @Test
  fun `Put preferences should store preferences and return DTO`() {

    telemetryService.trackEvent(TelemetryEventType.PREFERENCES_UPDATED, "user_id", "preferenceName", listOf("B10JQ", "B14AV"))

    var properties = mapOf("userId" to "user_id", "preferenceName" to "preferenceName", "values" to "B10JQ, B14AV")

    verify(telemetryClient).trackEvent("UserPreferencesUpdated", properties, emptyMap())
  }
}
