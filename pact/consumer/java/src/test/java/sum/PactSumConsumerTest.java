package sum;

import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pact.PactConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static au.com.dius.pact.consumer.ConsumerPactBuilder.consumer;
import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static au.com.dius.pact.model.MockProviderConfig.createDefault;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PactSumConsumerTest {

	@BeforeEach
	void setLocationWhereToPutTheGeneratedPactFile() {
		PactConfiguration.setOutputLocationInSystemProperties();
	}

	@Test
	void validateAndWritePacts() {
		List<ConsumerArguments> fragments = Arrays.asList(sumDuringNormalOperations(), sumDuringMaintanance());
		fragments.forEach(arguments -> {
			MockProviderConfig config = createDefault(PactSpecVersion.V3);
			PactVerificationResult result = runConsumerTest(arguments.pact, config, arguments.pactTestRun);
			checkResult(result);
		});
	}

	private ConsumerArguments sumDuringNormalOperations() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;charset=utf-8");

		DslPart requestBody = newJsonBody((o) -> {
			o.array("summands", (s) -> {
				s.numberValue(43).numberValue(42);
			});
		}).build();

		DslPart responseBody = newJsonBody((o) -> {
			o.numberValue("result", 85);
		}).build();

		RequestResponsePact pact =
				consumer("SumService")
						.hasPactWith("CalculatorService")
						.given("calculator online")
						.uponReceiving("sum two numbers")
						.path("/basic/addition")
						.body(requestBody)
						.method("POST")
						.willRespondWith()
						.headers(headers)
						.status(200)
						.body(responseBody)
						.toPact();

		return new ConsumerArguments(pact, mockServer -> {
			SumClient providerHandler = new SumClient(mockServer.getUrl());
			Number sum = providerHandler.sum(43, 42);
			assertThat(sum).isEqualTo(85);
		});
	}

	private ConsumerArguments sumDuringMaintanance() {
		DslPart requestBody = newJsonBody((o) -> {
			o.array("summands", (s) -> {
				s.numberValue(43).numberValue(42);
			});
		}).build();

		RequestResponsePact pact = pactWithCalculatorService()
				.given("calculator offline")
				.uponReceiving("sum two numbers")
				.method("POST")
				.path("/basic/addition")
				.body(requestBody)
				.willRespondWith()
				.status(503)
				.toPact();

		return new ConsumerArguments(pact, mockServer -> {
			assertThrows(CalculatorOffline.class, () -> new SumClient(mockServer.getUrl()).sum(43, 42));
		});
	}

	private PactDslWithProvider pactWithCalculatorService() {
		return consumer("SumService")
				.hasPactWith("CalculatorService");
	}

	private void checkResult(PactVerificationResult result) {
		if (result instanceof PactVerificationResult.Error) {
			throw new RuntimeException(((PactVerificationResult.Error) result).getError());
		}
		assertEquals(PactVerificationResult.Ok.INSTANCE, result);
	}
}
