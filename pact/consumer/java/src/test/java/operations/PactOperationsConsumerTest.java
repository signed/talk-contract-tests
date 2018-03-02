package operations;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pact.PactConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

public class PactOperationsConsumerTest {

	@Rule
	public final PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("CalculatorService", this);

	@Before
	public void setUp() {
		PactConfiguration.setOutputLocationInSystemProperties();
	}

	@Pact(consumer = "OperationsService")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return builder
				.given("calculator online")
				.uponReceiving("power down calculator")
				.path("/calculator/power")
				.method("DELETE")
				.willRespondWith()
				.status(200)
				.body("{\"status\": \"off\"}")
				.toPact();
	}

	@Test
	@PactVerification
	public void runTest() {
		OperationsClient operationsClient = new OperationsClient(mockProvider.getUrl());
		OperationsClient.CalculatorStatus status = operationsClient.powerDown();
		assertThat(status.status).isEqualTo("off");
	}
}
