package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller.PreferencesDTO
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.PreferenceRepository

@ExtendWith(MockitoExtension::class)
class PreferencesServiceTest {
  @Mock
  lateinit var preferenceRepository: PreferenceRepository


  @InjectMocks
  lateinit var preferenceService: PreferencesService

  private val preferencesList = listOf(
    Preference("user_id", "preference_name", "value_1"),
    Preference("user_id", "preference_name", "value_2"),
    Preference("user_id", "preference_name", "value_3")
  )

  private val preferencesDTO = PreferencesDTO(listOf("value_1", "value_2", "value_3"))

  @Test
  fun `Put preferences should store preferences and return DTO`() {
    whenever(
      preferenceRepository.saveAll(
        preferencesList
      )
    ).thenReturn(preferencesList)

    val savedPreferences = preferenceService.putPreferences("user_id", "preference_name", preferencesDTO)

    verifyNoMoreInteractions(preferenceRepository)

    assertThat(savedPreferences).isEqualTo(preferencesDTO)
  }

  @Test
  fun `Get preferences should retrieve existing preferences` () {
    whenever(preferenceRepository.findByHmppsUserIdAndName("user_id", "preference_name"))
      .thenReturn(preferencesList)

    val preferences = preferenceService.getPreferences("user_id", "preference_name")

    verifyNoMoreInteractions(preferenceRepository)
    assertThat(preferences).isEqualTo(preferencesDTO)
  }
}