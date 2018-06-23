package calculator;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import calculator.power.InMemoryOnlineStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("CalculatorService")
@PactFolder("../../pacts")
//@PactBroker(host = "pactbroker", port = "80")
class CalculatorPactProviderTest {

    private final OnlineStatus onlineStatus = new InMemoryOnlineStatus();
	private final CalculatorService calculatorService = CalculatorService.onRandomPort(onlineStatus);

	@BeforeAll
    static void setUpService() {
		String providerVersionPropertyName = "pact.provider.version";
		if (!System.getProperties().stringPropertyNames().contains(providerVersionPropertyName)) {
			System.getProperties().setProperty(providerVersionPropertyName, "0.0.1");
		}
	}

    @BeforeEach
    void before(PactVerificationContext context) {
        calculatorService.start();

        context.setTarget(new HttpTestTarget("localhost", calculatorService.port()));
    }

	@AfterEach
    void tearDown() {
		calculatorService.shutdown();
	}

	@State("calculator online")
	void calculatorOnline() {
		onlineStatus.powerOn();
	}

	@State("calculator offline")
	void calculatorOffline() {
		onlineStatus.powerOff();
	}

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

}