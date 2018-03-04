package sum;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
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

    @Test
    @PactVerification(fragment = "requestForSumWhileCalculatorOnline")
    public void validateSumWhileCalculatorOnlineTest() {
        SumClient providerHandler = new SumClient(mockProvider.getUrl());
        Double sum = providerHandler.sum(43.5, 42.2);
        assertThat(sum).isEqualTo(85.7);
    }

    @Pact(consumer = "SumService")
    public RequestResponsePact requestForSumWhileCalculatorOnline(PactDslWithProvider builder) {

        DslPart requestBody = newJsonBody((o) -> {
            o.array("summands", (summands) -> {
                summands.decimalType(43.5).decimalType(42.2);
            });
        }).build();

        DslPart responseBody = newJsonBody((body) -> {
            body.decimalType("result", 85.7);
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

    @Test
    @PactVerification(fragment = "requestForSumWhileCalculatorInMaintenance")
    public void validateSumWhileCalculatorInMaintenanceTest() {
        assertThrows(CalculatorOffline.class, () -> new SumClient(mockProvider.getUrl()).sum(0.0, 0.0));
    }

    @Pact(consumer = "SumService")
    public RequestResponsePact requestForSumWhileCalculatorInMaintenance(PactDslWithProvider builder) {

        DslPart requestBody = newJsonBody((body) -> {
            body.array("summands", (summands) -> {
                summands.decimalType(anyDecimal()).decimalType(anyDecimal());
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

    private double anyDecimal() {
        return 0.0;
    }

    private Map<String, String> contentTypeUtf8EncodedJson() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        return headers;
    }

}
