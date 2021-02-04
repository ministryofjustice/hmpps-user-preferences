package uk.gov.justice.digital.hmpps.hmppsuserpreferences.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Users
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository.UsersRepository
import java.util.UUID

@SpringBootTest
class CourtsControllerTest {

  lateinit var mvc: MockMvc
  lateinit var testUuid: UUID
  lateinit var user: Users

  @InjectMocks
  lateinit var controller: CourtsController

  @Mock
  lateinit var respository: UsersRepository

  @BeforeEach
  fun setup() {
    MockitoAnnotations.openMocks(this)
    testUuid = UUID.randomUUID()
    user = Users(testUuid, listOf("", ""))
    mvc = MockMvcBuilders.standaloneSetup(controller)
      .setMessageConverters(MappingJackson2HttpMessageConverter())
      .build()
    whenever(respository.save(any())).thenReturn(user)
  }

  @AfterEach
  fun teardown() {
    MockitoAnnotations.openMocks(this).close()
  }

  @Test
  fun `PUT courts returns created`() {
    val jsonData = jacksonObjectMapper().writeValueAsString(user)
    mvc.perform(
      MockMvcRequestBuilders.put("/users/$testUuid/preferences/courts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonData)
    )
      .andExpect(MockMvcResultMatchers.status().isCreated)
      .andDo(MockMvcResultHandlers.print())
      .andReturn()
  }
}
