package uk.gov.justice.digital.hmpps.hmppsuserpreferences.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.model.Users

@Repository
interface UsersRepository : CrudRepository<Users, Long>
