package uk.gov.justice.digital.hmpps.hmppsuserpreferences.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "preference")
data class Preference(
  @Column(nullable = false)
  val hmppsUserId: String,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val value: String,
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L
}
