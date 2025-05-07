package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference

private const val USER_ID = "user_id"

private const val PREFERENCE_NAME = "court"

@DataJpaTest
class PreferenceTest {

  @Autowired
  lateinit var preferenceRepository: PreferenceRepository

  @Test
  fun `Test save preference`() {
    val preference = savePreference(USER_ID, PREFERENCE_NAME, "SHF")
    val actual = preferenceRepository.findById(preference.id)

    assertThat(actual).isNotEmpty
    assertThat(actual.get()).isEqualTo(preference)
  }

  @Test
  fun `Test find by hmppsUserId and preferenceName`() {
    val pref1 = savePreference(USER_ID, PREFERENCE_NAME, "Sheffield")
    val pref2 = savePreference(USER_ID, PREFERENCE_NAME, "North Tyneside")
    savePreference(USER_ID, "Something else", "FOO")
    savePreference("SOMEONE ELSE", PREFERENCE_NAME, "FOO")

    val preferences =
      preferenceRepository.findByHmppsUserIdAndName(USER_ID, PREFERENCE_NAME)

    assertThat(preferences.size).isEqualTo(2)
    assertThat(preferences).contains(pref1)
    assertThat(preferences).contains(pref2)
  }

  private fun savePreference(
    hmppsUserId: String,
    name: String,
    value: String,
  ): Preference {
    val preference = Preference(hmppsUserId, name, value)
    preferenceRepository.save(preference)
    return preference
  }
}
