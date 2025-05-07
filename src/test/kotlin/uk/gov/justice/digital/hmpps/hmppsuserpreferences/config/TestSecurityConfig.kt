package uk.gov.justice.digital.hmpps.hmppsuserpreferences.config

import com.nimbusds.jwt.JWTParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter

@Configuration
class TestSecurityConfig {

  @Bean
  @Profile("test")
  fun testJwtDecoder(): JwtDecoder = TestJwtDecoder()
}

internal class TestJwtDecoder : JwtDecoder {
  private val claimSetConverter = MappedJwtClaimSetConverter.withDefaults(emptyMap())

  override fun decode(token: String): Jwt? {
    // extract headers and claims, but do not attempt to verify signature
    val jwt = JWTParser.parse(token)
    val headers = LinkedHashMap<String, Any>(jwt.header.toJSONObject())
    val claims = claimSetConverter.convert(jwt.getJWTClaimsSet().claims)

    return Jwt.withTokenValue(token)
      .headers { it.putAll(headers) }
      .claims { it.putAll(claims) }
      .build()
  }
}
