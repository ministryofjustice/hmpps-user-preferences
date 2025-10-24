package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller.PreferencesDTO
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.PreferenceRepository

@Service
class PreferencesService {

  @Autowired
  lateinit var preferenceRepository: PreferenceRepository

  @Autowired
  lateinit var telemetryService: TelemetryService

  fun getPreferences(userId: String, preferenceName: String): PreferencesDTO {
    val preferences = preferenceRepository.findByHmppsUserIdAndName(userId, preferenceName)
    return PreferencesDTO(preferences.map { preference -> preference.value })
  }

  @Transactional
  fun putPreferences(userId: String, preferenceName: String, preferencesDto: PreferencesDTO): PreferencesDTO {
    val preferences = preferencesDto.items
      .map { item -> Preference(userId, preferenceName, item) }

    val previousPreferenceValues = preferenceRepository.findByHmppsUserIdAndName(userId, preferenceName)
    preferenceRepository.deleteAll(previousPreferenceValues)
    preferenceRepository.saveAll(preferences)
    telemetryService.trackEvent(TelemetryEventType.PREFERENCES_UPDATED, userId, preferenceName, preferencesDto.items, previousPreferenceValues.map { preference -> preference.value })
    return preferencesDto
  }

  @Transactional
  fun deleteCourts(userId: String) {
    val currentCourts = preferenceRepository.findByHmppsUserIdAndName(userId, "courts")
    preferenceRepository.deleteAll(currentCourts)
    telemetryService.trackEvent(
      TelemetryEventType.PREFERENCES_UPDATED,
      userId,
      "courts",
      listOf<String>(),
      currentCourts.map { court -> court.value },
    )
  }
}
