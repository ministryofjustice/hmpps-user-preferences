package uk.gov.justice.digital.hmpps.hmppsuserpreferences.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller.PreferencesDTO
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.PreferenceRepository

@ExtendWith(MockitoExtension::class)
class PreferencesServiceTest {
  @Mock
  lateinit var preferenceRepository: PreferenceRepository

  @Mock
  lateinit var telemetryService: TelemetryService

  @InjectMocks
  lateinit var preferenceService: PreferencesService

  private val preferencesList = listOf(
    Preference("user_id", "preference_name", "value_1"),
    Preference("user_id", "preference_name", "value_2"),
    Preference("user_id", "preference_name", "value_3"),
  )

  private val preferencesDTO = PreferencesDTO(listOf("value_1", "value_2", "value_3"))

  @Test
  fun `Put preferences should store preferences and return DTO`() {
    whenever(
      preferenceRepository.saveAll(
        preferencesList,
      ),
    ).thenReturn(preferencesList)
    whenever(preferenceRepository.findByHmppsUserIdAndName("user_id", "preference_name"))
      .thenReturn(emptyList())

    val savedPreferences = preferenceService.putPreferences("user_id", "preference_name", preferencesDTO)

    verify(preferenceRepository).deleteAll(emptyList())
    verify(telemetryService).trackEvent(
      TelemetryEventType.PREFERENCES_UPDATED,
      "user_id",
      "preference_name",
      preferencesDTO.items,
      emptyList(),
    )
    verifyNoMoreInteractions(preferenceRepository, telemetryService)

    assertThat(savedPreferences).isEqualTo(preferencesDTO)
  }

  @Test
  fun `Put preferences should overwrite existing preferences and return DTO`() {
    whenever(
      preferenceRepository.saveAll(
        preferencesList,
      ),
    ).thenReturn(preferencesList)
    whenever(preferenceRepository.findByHmppsUserIdAndName("user_id", "preference_name"))
      .thenReturn(preferencesList)

    val savedPreferences = preferenceService.putPreferences("user_id", "preference_name", preferencesDTO)

    verify(preferenceRepository).deleteAll(preferencesList)
    verify(preferenceRepository).saveAll(preferencesList)
    verify(telemetryService).trackEvent(
      TelemetryEventType.PREFERENCES_UPDATED,
      "user_id",
      "preference_name",
      preferencesDTO.items,
      preferencesList.map { preference -> preference.value },
    )
    verifyNoMoreInteractions(preferenceRepository, telemetryService)

    assertThat(savedPreferences).isEqualTo(preferencesDTO)
  }

  @Test
  fun `Get preferences should retrieve existing preferences`() {
    whenever(preferenceRepository.findByHmppsUserIdAndName("user_id", "preference_name"))
      .thenReturn(preferencesList)

    val preferences = preferenceService.getPreferences("user_id", "preference_name")

    verifyNoMoreInteractions(preferenceRepository)
    assertThat(preferences).isEqualTo(preferencesDTO)
  }
}
