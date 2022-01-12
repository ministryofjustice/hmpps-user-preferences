package uk.gov.justice.digital.hmpps.hmppsuserpreferences.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringdocConfig {
  @Bean
  fun openAPI(buildProperties: BuildProperties): OpenAPI? {
    return OpenAPI()
      .info(
        Info().title("HMPPS User Preferences API")
          .description("A simple service for storing user preferences associated with an hmpps-auth account.")
          .version(buildProperties.version)
          .license(
            License().name("MIT License")
              .url("https://github.com/ministryofjustice/hmpps-user-preferences/blob/main/LICENSE")
          )
      )
  }
}
