package uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.service.PreferencesService

data class PreferencesDTO(
  val items: List<String>
)

@Api(tags = ["User preference resources"])
@RestController
class PreferencesController {
  @Autowired
  lateinit var preferencesService: PreferencesService

  @ApiOperation(value = "Gets a user's preferences by preference name")
  @GetMapping(
    value = ["/users/{userId}/preferences/{preferenceName}"],
    produces = [APPLICATION_JSON_VALUE]
  )
  fun getPreferences(@PathVariable userId: String, @PathVariable preferenceName: String): PreferencesDTO {
    return preferencesService.getPreferences(userId, preferenceName)
  }

  @ApiOperation(value = "Put a user's preferences by preference name")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PutMapping("/users/{userId}/preferences/{preferenceName}")
  fun putPreferences(
    @PathVariable userId: String,
    @PathVariable preferenceName: String,
    @RequestBody preferences: PreferencesDTO
  ): PreferencesDTO {
    return preferencesService.putPreferences(userId, preferenceName, preferences)
  }
}
