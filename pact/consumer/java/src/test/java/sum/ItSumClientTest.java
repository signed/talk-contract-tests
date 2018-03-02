package sum;

import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ItSumClientTest {

	private final String calculatorServiceUrl = "http://localhost:9090";
	private final SumClient sumClient = new SumClient(calculatorServiceUrl);

	@Test
	void sumOfTwoNumbers() {
		assumeTrue(this::calculatorOnline, "Did you start the calculator?");

		assertThat(sumClient.sum(1, 1).intValue()).isEqualTo(2);
	}

	private boolean calculatorOnline() {
		try {
			sumClient.sum(0, 0);
			return true;
		} catch (RuntimeException ex) {
			return !(ex.getCause() instanceof ConnectException);
		}
	}
}
