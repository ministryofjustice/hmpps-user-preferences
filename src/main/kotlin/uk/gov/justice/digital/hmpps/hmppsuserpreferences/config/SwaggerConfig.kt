package uk.gov.justice.digital.hmpps.hmppsuserpreferences.config

import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.StringVendorExtension
import springfox.documentation.service.VendorExtension
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.Properties

@Configuration
@EnableSwagger2
class SwaggerConfig {

  @Autowired
  private val applicationContext: ApplicationContext? = null

  @Bean
  fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any())
      .build()
  }

  private fun apiInfo(): ApiInfo? {
    val vendorExtension = StringVendorExtension("", "")
    val vendorExtensions: MutableCollection<VendorExtension<*>> = ArrayList()
    vendorExtensions.add(vendorExtension)
    return ApiInfo(
      "HMPPS User Preferences API Documentation",
      "REST service for storing user preferences",
      getVersion()?.version,
      "https://gateway.nomis-api.service.justice.gov.uk/auth/terms",
      contactInfo(),
      "Open Government Licence v3.0",
      "https://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/",
      vendorExtensions
    )
  }

  private fun getVersion(): BuildProperties? {
    return try {
      applicationContext!!.getBean("buildProperties") as BuildProperties
    } catch (be: BeansException) {
      val properties = Properties()
      properties["version"] = "?"
      BuildProperties(properties)
    }
  }

  private fun contactInfo(): Contact? {
    return Contact(
      "HMPPS Probation in Court Team",
      "https://github.com/ministryofjustice/hmpps-user-preferences",
      "pictsupport@digital.justice.gov.uk"
    )
  }
}
