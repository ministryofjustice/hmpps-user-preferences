package uk.gov.justice.digital.hmpps.hmppsuserpreferences

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.service.UsersService
import java.util.*
import kotlin.collections.HashMap

@RestController
class PreferencesController {

  @Autowired
  lateinit var usersService: UsersService

  @GetMapping(value = ["/users/{uuid}/preferences/{preferenceId}"],
              produces = [APPLICATION_JSON_VALUE]
  )
  fun getPreferences(@PathVariable uuid: String, @PathVariable preferenceId: String): Map<String, List<String>> {
    val prefs = HashMap<String, List<String>>()
    prefs.put(preferenceId, usersService.getPreferenceValues(uuid, preferenceId))
    return prefs
  }
}
