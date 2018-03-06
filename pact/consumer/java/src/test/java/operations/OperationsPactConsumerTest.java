package operations;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pact.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationsPactConsumerTest {

    @Rule
    public final PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("CalculatorService", PactSpecVersion.V3, this);

    @Before
    public void setUp() {
        Configuration.setOutputLocationInSystemProperties();
    }

    @Test
    @PactVerification(fragment = "powerDownCalculator")
    public void runTest() {
        OperationsClient operationsClient = new OperationsClient(mockProvider.getUrl());
        OperationsClient.CalculatorStatus status = operationsClient.powerDown();
        assertThat(status.status).isEqualTo("off");
    }

    @Pact(consumer = "OperationsService")
    public RequestResponsePact powerDownCalculator(PactDslWithProvider builder) {
        return builder
            .given("calculator online")
                .uponReceiving("power down calculator")
                .method("DELETE")
                .path("/calculator/power")
                .willRespondWith()
            .status(200)
                .body("{\"status\": \"off\"}")
            .toPact();
    }
}
