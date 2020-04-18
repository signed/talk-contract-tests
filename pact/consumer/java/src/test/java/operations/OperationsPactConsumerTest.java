package operations;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pact.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CalculatorService", port = "0")
class OperationsPactConsumerTest {

    @BeforeEach
    void setUp() {
        Configuration.setOutputLocationInSystemProperties();
    }

    @Test
    void runTest(MockServer mockServer) {
        OperationsClient operationsClient = new OperationsClient(mockServer.getUrl());
        OperationsClient.CalculatorStatus status = operationsClient.powerDown();
        assertThat(status.status).isEqualTo("off");
    }

    @Pact(provider = "CalculatorService", consumer = "OperationsService")
    RequestResponsePact powerDownCalculator(PactDslWithProvider builder) {
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
