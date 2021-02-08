package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.UsersRepository

@Service
class UsersService {

  lateinit var usersRepository: UsersRepository

  fun getPreferenceValues(userId: String, preferenceId: String): List<String> {
    return emptyList()
  }

}
