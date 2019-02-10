package sum;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pact.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CalculatorService", port = "0")
class SumPactConsumerTest {

    @BeforeEach
    void setUp() {
        Configuration.setOutputLocationInSystemProperties();
    }

    @Test
    @PactTestFor(pactMethod = "requestForSumWhileCalculatorOnline")
    void validateSumWhileCalculatorOnlineTest(MockServer mockServer) {
        SumClient providerHandler = new SumClient(mockServer.getUrl());
        Double sum = providerHandler.sum(43.5, 42.2);
        assertThat(sum).isEqualTo(85.7);
    }

    @Pact(provider = "CalculatorService", consumer = "SumService")
    RequestResponsePact requestForSumWhileCalculatorOnline(PactDslWithProvider builder) {
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
    @PactTestFor(pactMethod = "requestForSumWhileCalculatorInMaintenance")
    void validateSumWhileCalculatorInMaintenanceTest(MockServer mockServer) {
        assertThrows(CalculatorOffline.class, () -> new SumClient(mockServer.getUrl()).sum(0.0, 0.0));
    }

    @Pact(provider = "CalculatorService", consumer = "SumService")
    RequestResponsePact requestForSumWhileCalculatorInMaintenance(PactDslWithProvider builder) {
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
