package uk.gov.justice.digital.hmpps.hmppsuserpreferences.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@Profile("unsecured")
class UnsecuredResourceServerConfiguration {

  @Bean
  fun configure(http: HttpSecurity): SecurityFilterChain {
    http
      // Can't have CSRF protection as requires session
      .csrf().disable()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    return http.build()
  }
}
