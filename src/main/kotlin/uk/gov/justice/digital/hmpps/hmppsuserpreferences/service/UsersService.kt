package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller.PreferencesDTO
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.UsersRepository

@Service
class UsersService {

  lateinit var usersRepository: UsersRepository

  fun getPreferences(userId: String, preferenceId: String): PreferencesDTO {
    // TODO: Make call to usersRepository to get preferences
    return PreferencesDTO(emptyList())
  }

  fun putPreferences(userId: String, courtKey: String, items: PreferencesDTO): PreferencesDTO {
    // TODO: Make call to usersRepository to put preferences
    return PreferencesDTO(emptyList())
  }
}
