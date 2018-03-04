package sum;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pact.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SumPactConsumerTest {

    @Rule
    public final PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("CalculatorService", this);

    @Before
    public void setUp() {
        Configuration.setOutputLocationInSystemProperties();
    }

    @Pact(consumer = "SumService")
    public RequestResponsePact requestForSumWhileCalculatorOnline(PactDslWithProvider builder) {

        DslPart requestBody = newJsonBody((o) -> {
            o.array("summands", (s) -> {
                s.numberValue(43).numberValue(42);
            });
        }).build();

        DslPart responseBody = newJsonBody((o) -> {
            o.numberValue("result", 85);
        }).build();

        return builder
            .given("calculator online")
            .uponReceiving("sum two numbers")
            .path("/basic/addition")
            .headers(contentTypeUtf8EncodedJson())
            .body(requestBody)
            .method("POST")
            .willRespondWith()
            .headers(contentTypeUtf8EncodedJson())
            .status(200)
            .body(responseBody)
            .toPact();
    }

    private Map<String, String> contentTypeUtf8EncodedJson() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        return headers;
    }

    @Test
    @PactVerification(fragment = "requestForSumWhileCalculatorOnline")
    public void validateSumWhileCalculatorOnlineTest() {
        SumClient providerHandler = new SumClient(mockProvider.getUrl());
        Number sum = providerHandler.sum(43, 42);
        assertThat(sum).isEqualTo(85);
    }

    @Pact(consumer = "SumService")
    public RequestResponsePact requestForSumWhileCalculatorInMaintenance(PactDslWithProvider builder) {

        DslPart requestBody = newJsonBody((o) -> {
            o.array("summands", (s) -> {
                s.numberValue(43).numberValue(42);
            });
        }).build();

        return builder.given("calculator offline")
            .uponReceiving("sum two numbers")
            .path("/basic/addition")
            .method("POST")
            .headers(contentTypeUtf8EncodedJson())
            .body(requestBody)
            .willRespondWith()
            .status(503)
            .toPact();
    }

    @Test
    @PactVerification(fragment = "requestForSumWhileCalculatorInMaintenance")
    public void validateSumWhileCalculatorInMaintenanceTest() {
        assertThrows(CalculatorOffline.class, () -> new SumClient(mockProvider.getUrl()).sum(43, 42));
    }

}
