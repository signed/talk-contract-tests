package calculator;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(PactRunner.class)
@Provider("CalculatorService")
//@PactFolder("../../pacts")
@PactBroker(host = "pactbroker", port = "80")
public class PactCalculatorProviderTest {

	private final OnlineStatusFake onlineStatus = new OnlineStatusFake();
	private final Calculator calculator = Calculator.onRandomPort(onlineStatus);

	@BeforeClass
	public static void setUpService() {
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
		calculator.start();
	}

	@After
	public void tearDown() {
		calculator.shutdown();
	}

	@State(value = {"calculator online"})
	public void toDefaultState() {
		System.out.println("calculator online");
		onlineStatus.online();
	}

	@State("with-data")
	public void toStateWithData(Map<String, Object> data) {
		// Prepare service before interaction that require "with-data" state. The provider state data will be passed
		// in the data parameter; This seems to be a pact-jvm only feature
		// ...
		System.out.println("Now service in state using data " + data);
	}

	@TestTarget
	public final MutablePortHttpTarget target = new MutablePortHttpTarget(calculator::port);

	private static class OnlineStatusFake implements OnlineStatus {
		private boolean online = true;

		public void offline() {
			online = false;
		}

		public void online() {
			online = true;
		}

		@Override
		public boolean isOnline() {
			return online;
		}
	}
}