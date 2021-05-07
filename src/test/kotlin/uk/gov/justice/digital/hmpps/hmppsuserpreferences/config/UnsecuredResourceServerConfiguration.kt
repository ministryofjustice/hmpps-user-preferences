package uk.gov.justice.digital.hmpps.hmppsuserpreferences.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
@Profile("unsecured")
class UnsecuredResourceServerConfiguration : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http
      // Can't have CSRF protection as requires session
      .csrf().disable()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }
}
