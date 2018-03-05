package calculator;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.TestTarget;
import calculator.power.InMemoryOnlineStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(PactRunner.class)
@Provider("CalculatorService")
@PactFolder("../../pacts")
//@PactBroker(host = "pactbroker", port = "80")
public class CalculatorPactProviderTest {

    private final OnlineStatus onlineStatus = new InMemoryOnlineStatus();
	private final CalculatorService calculatorService = CalculatorService.onRandomPort(onlineStatus);

	@BeforeClass
	public static void setUpService() {
        System.out.println("before class");
		//Run DB, create schema
		//Run service
		//...
		String providerVersionPropertyName = "pact.provider.version";
		if (!System.getProperties().stringPropertyNames().contains(providerVersionPropertyName)) {
			System.getProperties().setProperty(providerVersionPropertyName, "0.0.1");
		}
	}

	@Before
	public void before() {
		// Rest data
		// Mock dependent service responses
		// ...
		System.out.println("before");
		calculatorService.start();
	}

	@After
	public void tearDown() {
		calculatorService.shutdown();
	}

	@State("calculator online")
	public void calculatorOnline() {
		System.out.println("calculator online");
		onlineStatus.powerOn();
	}

	@State("calculator offline")
	public void calculatorOffline() {
		System.out.println("calculator offline");
		onlineStatus.powerOff();
	}

	@TestTarget
	public final MutablePortHttpTarget target = new MutablePortHttpTarget(calculatorService::port);

}