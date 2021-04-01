package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import com.microsoft.applicationinsights.TelemetryClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TelemetryService {

  @Autowired
  lateinit var telemetryClient: TelemetryClient

  fun trackEvent(eventType: TelemetryEventType, userId: String, preferenceName: String, preferenceValues: List<String>) {

    var properties = mapOf("userId" to userId, "preferenceName" to preferenceName, "values" to preferenceValues.joinToString())

    telemetryClient.trackEvent(eventType.eventName, properties, emptyMap())
  }
}
