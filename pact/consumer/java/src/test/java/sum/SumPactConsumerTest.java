package sum;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pact.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SumPactConsumerTest {

    @Rule
    public final PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("CalculatorService", PactSpecVersion.V3,this);

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
        return builder
            .given("calculator online")
                .uponReceiving("sum two numbers")
                .method("POST")
                .path("/basic/addition")
                .headers(contentTypeUtf8EncodedJson())
                .body(sumOf(43.5, 42.2))
            .willRespondWith()
                .headers(contentTypeUtf8EncodedJson())
                .status(SC_OK)
                .body(resultOf(85.7))
            .toPact();
    }

    @Test
    @PactVerification(fragment = "requestForSumWhileCalculatorInMaintenance")
    public void validateSumWhileCalculatorInMaintenanceTest() {
        assertThrows(CalculatorOffline.class, () -> new SumClient(mockProvider.getUrl()).sum(0.0, 0.0));
    }

    @Pact(consumer = "SumService")
    public RequestResponsePact requestForSumWhileCalculatorInMaintenance(PactDslWithProvider builder) {
        return builder.given("calculator offline")
            .uponReceiving("sum two numbers")
                .method("POST")
                .path("/basic/addition")
                .headers(contentTypeUtf8EncodedJson())
                .body(sumOf(anyDecimal(), anyDecimal()))
            .willRespondWith()
                .status(SC_SERVICE_UNAVAILABLE)
            .toPact();
    }

    private Map<String, String> contentTypeUtf8EncodedJson() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        return headers;
    }

    private double anyDecimal() {
        return 0.0;
    }

    private DslPart sumOf(double first, double second) {
        return newJsonBody((o) -> {
            o.array("summands", (summands) -> {
                summands.decimalType(first).decimalType(second);
            });
        }).build();
    }

    private DslPart resultOf(double result) {
        return newJsonBody((body) -> {
            body.decimalType("result", result);
        }).build();
    }

}
