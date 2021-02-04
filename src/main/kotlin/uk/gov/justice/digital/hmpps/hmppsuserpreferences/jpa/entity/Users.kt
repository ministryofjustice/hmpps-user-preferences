package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity

import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.HashMap
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType::class)
class Users(
  @Id
  val id: UUID,

  @Type(type = "hstore")
  @Column(name = "properties", columnDefinition = "hstore")
  val properties: HashMap<String, String>
)
