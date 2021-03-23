package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller.PreferencesDTO
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.PreferenceRepository

@Service
class UsersService {

  lateinit var preferenceRespository: PreferenceRepository

  fun getPreferences(userId: String, preferenceId: String): PreferencesDTO {
    // TODO: Make call to usersRepository to get preferences
    return PreferencesDTO(emptyList())
  }

  fun putPreferences(userId: String, preferenceId: String, items: PreferencesDTO): PreferencesDTO {
    // TODO: Make call to usersRepository to put preferences
    return PreferencesDTO(emptyList())
  }
}
