package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Users
import java.util.UUID

@DataJpaTest
@ActiveProfiles("jpa-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest @Autowired constructor(
  val entityManager: TestEntityManager,
  val usersRepository: UsersRepository
) {

  val testUuid = UUID.randomUUID()

  @Test
  fun `Can create a new user with courts`() {
    val newUser = Users(testUuid, hashMapOf("courts" to "B10JQ, B62DC"))
    entityManager.persist(newUser)
    entityManager.flush()

    val created = usersRepository.save(newUser)
    Assertions.assertThat(created).isSameAs(newUser)
  }

  @Test
  fun `Can update user with empty court list`() {
    val newUser = Users(testUuid, hashMapOf())
    entityManager.persist(newUser)
    entityManager.flush()

    val created = usersRepository.save(newUser)
    Assertions.assertThat(created).isSameAs(newUser)
  }
}
