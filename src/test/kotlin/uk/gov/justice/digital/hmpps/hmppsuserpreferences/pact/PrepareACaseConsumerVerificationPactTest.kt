package uk.gov.justice.digital.hmpps.hmppsuserpreferences.pact

import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactBroker
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import uk.gov.justice.digital.hmpps.hmppsuserpreferences.integration.IntegrationTestBase

@Provider("hmpps-user-preferences")
@PactBroker
@ActiveProfiles("test", "unsecured")
class PrepareACaseConsumerVerificationPactTest : IntegrationTestBase() {

  @TestTemplate
  @ExtendWith(PactVerificationSpringProvider::class)
  fun pactVerificationTestTemplate(context: PactVerificationContext) {
    context.verifyInteraction()
  }

  @State("a user has selected relevant courts")
  fun getCases() {
  }
}
