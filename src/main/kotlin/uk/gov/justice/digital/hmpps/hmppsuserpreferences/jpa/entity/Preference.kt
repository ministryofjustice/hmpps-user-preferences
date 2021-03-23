package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "preference")
data class Preference(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long,

  @Column(nullable = false)
  val hmppsUserId: String,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val value: String
)
