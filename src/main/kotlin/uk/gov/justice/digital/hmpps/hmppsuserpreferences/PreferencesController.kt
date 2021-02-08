package uk.gov.justice.digital.hmpps.hmppsuserpreferences

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PreferencesController {
  @GetMapping(value = "/users/{uuid}/preferences/{preferenceId}", produces = APPLICATION_JSON_VALUE)
  public @ResponseBody
  Map<String, List<String>>
  fun getPreferences() {}
}