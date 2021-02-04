package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Users

@Repository
interface UsersRepository : CrudRepository<Users, Long>
