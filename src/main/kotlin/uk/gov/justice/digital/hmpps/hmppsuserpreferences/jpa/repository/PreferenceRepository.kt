package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity.Preference

@Repository
interface PreferenceRepository : CrudRepository<Preference, Long>
