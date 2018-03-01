package calculator;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFolder;
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
public class PactJunitIntegrationTest {

	private final Calculator calculator = new Calculator();

	@BeforeClass
	public static void setUpService() {
		//Run DB, create schema
		//Run service
		//...
		String providerVersionPropertyName = "pact.provider.version";
		if(!System.getProperties().stringPropertyNames().contains(providerVersionPropertyName)){
			System.getProperties().setProperty(providerVersionPropertyName, "0.0.1");
		}
	}

	@Before
	public void before() {
		// Rest data
		// Mock dependent service responses
		// ...
		calculator.start();
	}

	@After
	public void tearDown() {
		calculator.shutdown();
	}

	@State(value = {"default", "no-data"})
	public void toDefaultState() {
		System.out.println("Now service in default state");
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
}