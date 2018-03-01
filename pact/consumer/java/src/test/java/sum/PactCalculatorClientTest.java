package sum;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static au.com.dius.pact.model.MockProviderConfig.createDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class PactCalculatorClientTest {

	@Before
	public void setLocationWhereToPutTheGeneratedPactFile() {
		System.getProperties().setProperty("pact.rootDir", "../../pacts");
	}

	@Test
	public void testWithPactDSLJsonBody() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;charset=utf-8");

		DslPart requestBody = LambdaDsl.newJsonBody((o) -> {
			o.array("summands", (s) -> {
				s.numberValue(43).numberValue(42);
			});
		}).build();

		DslPart responseBody = LambdaDsl.newJsonBody((o) -> {
			o.numberValue("result", 85);
		}).build();

		RequestResponsePact pact = ConsumerPactBuilder
				.consumer("SumService")
				.hasPactWith("CalculatorService")
				//.given("")
				.uponReceiving("sum two numbers")
				.path("/operations/sum")
				.body(requestBody)
				.method("POST")
				.willRespondWith()
				.headers(headers)
				.status(200)
				.body(responseBody)
				.toPact();

		MockProviderConfig config = createDefault(PactSpecVersion.V3);
		PactVerificationResult result = runConsumerTest(pact, config, mockServer -> {
			SumClient providerHandler = new SumClient(mockServer.getUrl());
			Number sum = providerHandler.sum(43, 42);
			assertThat(sum).isEqualTo(85);
		});

		checkResult(result);
	}

	private void checkResult(PactVerificationResult result) {
		if (result instanceof PactVerificationResult.Error) {
			throw new RuntimeException(((PactVerificationResult.Error) result).getError());
		}
		assertEquals(PactVerificationResult.Ok.INSTANCE, result);
	}
}
