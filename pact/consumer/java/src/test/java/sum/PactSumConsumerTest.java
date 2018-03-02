package sum;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.PactTestRun;
import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static au.com.dius.pact.model.MockProviderConfig.createDefault;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

class PactSumConsumerTest {

	@BeforeEach
	void setLocationWhereToPutTheGeneratedPactFile() {
		String pactDirectory = Paths.get("../../pacts").toAbsolutePath().normalize().toString();
		String key = "pact.rootDir";
		System.out.println(format("%s: %s", key, pactDirectory));
		System.getProperties().setProperty(key, pactDirectory);
	}

	public static class ConsumerPactArguments {

		final RequestResponsePact pact;
		final PactTestRun pactTestRun;

		ConsumerPactArguments(RequestResponsePact pact, PactTestRun pactTestRun) {
			this.pact = pact;
			this.pactTestRun = pactTestRun;
		}
	}

	@Test
	void validateAndWritePacts() {
		List<ConsumerPactArguments> fragments = Arrays.asList(sumDuringNormalOperations());
		fragments.forEach(arguments -> {
			MockProviderConfig config = createDefault(PactSpecVersion.V3);
			PactVerificationResult result = runConsumerTest(arguments.pact, config, arguments.pactTestRun);
			checkResult(result);
		});
	}

	private ConsumerPactArguments sumDuringNormalOperations() {
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
				.given("calculator online")
				.uponReceiving("sum two numbers")
				.path("/operations/sum")
				.body(requestBody)
				.method("POST")
				.willRespondWith()
				.headers(headers)
				.status(200)
				.body(responseBody)
				.toPact();

		return new ConsumerPactArguments(pact, mockServer -> {
			SumClient providerHandler = new SumClient(mockServer.getUrl());
			Number sum = providerHandler.sum(43, 42);
			assertThat(sum).isEqualTo(85);
		});
	}

	private void checkResult(PactVerificationResult result) {
		if (result instanceof PactVerificationResult.Error) {
			throw new RuntimeException(((PactVerificationResult.Error) result).getError());
		}
		assertEquals(PactVerificationResult.Ok.INSTANCE, result);
	}
}
