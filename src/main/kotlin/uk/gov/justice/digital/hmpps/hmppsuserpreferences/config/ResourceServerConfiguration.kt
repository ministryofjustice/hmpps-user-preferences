package uk.gov.justice.digital.hmpps.hmppsuserpreferences.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@Profile("!unsecured")
class ResourceServerConfiguration {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().csrf().disable()
      .authorizeHttpRequests {
        it.requestMatchers(
          "/health/**",
          "/info",
          "/health",
          "/ping",
          "/swagger-resources/**",
          "/v3/api-docs/**",
          "/swagger-ui.html",
          "/swagger-ui/**",
        ).permitAll()
        it.anyRequest().authenticated()
      }
      .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())

    return http.build()
  }

  fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
    // hmpps auth tokens have roles in a custom `authorities` claim.
    // the authorities are already prefixed with `ROLE_`.
    val grantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
    grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities")
    grantedAuthoritiesConverter.setAuthorityPrefix("")

    val jwtAuthenticationConverter = JwtAuthenticationConverter()
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
    return jwtAuthenticationConverter
  }
}
