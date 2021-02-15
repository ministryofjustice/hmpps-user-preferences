package uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.service.UsersService

data class PreferencesDTO(
  val items: List<String>
)

@RestController
class PreferencesController {
  @Autowired
  lateinit var usersService: UsersService

  @GetMapping(
    value = ["/users/{userId}/preferences/{preferenceId}"],
    produces = [APPLICATION_JSON_VALUE]
  )
  fun getPreferences(@PathVariable userId: String, @PathVariable preferenceId: String): PreferencesDTO {
    return usersService.getPreferences(userId, preferenceId)
  }

  @ResponseStatus(value = HttpStatus.CREATED)
  @PutMapping("/users/{userId}/preferences/{preferenceId}")
  fun putPreferences(
    @PathVariable userId: String,
    @PathVariable preferenceId: String,
    @RequestBody preferences: PreferencesDTO
  ): PreferencesDTO {
    return usersService.putPreferences(userId, preferenceId, preferences)
  }
}
