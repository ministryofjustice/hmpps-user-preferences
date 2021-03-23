package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference

@DataJpaTest
class PreferenceTest(
  @Autowired
  val preferenceRepository: PreferenceRepository,
) {
  @Test
  fun `Test save preference`() {
    val preference = Preference(1L, "user_id", "court", "SHF")
    preferenceRepository.save(preference)
    val actual = preferenceRepository.findById(1L)

    assertThat(actual).isNotEmpty
    assertThat(actual.get()).isEqualTo(preference)
  }
}
