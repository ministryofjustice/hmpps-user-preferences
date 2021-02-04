package uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.dto.CourtsDTO
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Users
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.UsersRepository
import java.util.UUID

@RestController
class CourtsController {

  @Autowired
  lateinit var repository: UsersRepository

  @ResponseStatus(value = HttpStatus.CREATED)
  @PutMapping("/users/{uuid}/preferences/courts")
  fun updatePreferences(@PathVariable uuid: UUID, @RequestBody preference: CourtsDTO): List<String> {
    repository.save(Users(uuid, hashMapOf("courts" to preference.courts.joinToString())))
    return preference.courts
  }
}
