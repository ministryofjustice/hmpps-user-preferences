package uk.gov.justice.digital.hmpps.hmppsuserpreferences.model

import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@TypeDef(name = "list-array", typeClass = ListArrayType::class)
data class Users(
  @Id val id: UUID? = null,
  @Type(type = "list-array") @Column(name = "courts", columnDefinition = "text[]")
  var courts: List<String>? = null
)
