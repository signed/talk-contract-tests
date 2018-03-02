package sum;

import au.com.dius.pact.consumer.PactTestRun;
import au.com.dius.pact.model.RequestResponsePact;

public class ConsumerArguments {

	final RequestResponsePact pact;
	final PactTestRun pactTestRun;

	ConsumerArguments(RequestResponsePact pact, PactTestRun pactTestRun) {
		this.pact = pact;
		this.pactTestRun = pactTestRun;
	}
}
