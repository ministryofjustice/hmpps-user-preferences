package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller.PreferencesDTO
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.PreferenceRepository

@Service
class PreferencesService {

  lateinit var preferenceRepository: PreferenceRepository

  fun getPreferences(userId: String, preferenceName: String): PreferencesDTO {
    val preferences = preferenceRepository.findByHmppsUserIdAndName(userId, preferenceName)
    return PreferencesDTO(preferences.map { preference -> preference.value })
  }

  fun putPreferences(userId: String, preferenceName: String, preferencesDto: PreferencesDTO): PreferencesDTO {
    val preferences = preferencesDto.items
      .map { item -> Preference(userId, preferenceName, item) }

    preferenceRepository.saveAll(preferences)
    return preferencesDto
  }
}
