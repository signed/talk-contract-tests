package sum;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.ConnectException;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ItSumClientTest {

	private final String calculatorServiceUrl = "http://localhost:9090";
	private final SumClient sumClient = new SumClient(calculatorServiceUrl);

	@Test
	void sumOfTwoNumbers() {
		assumeTrue(this::calculatorOnline, "Did you start the calculator?");

		assertThat(sumClient.sum(1.0, 10.0)).isEqualTo(BigDecimal.valueOf(11));
	}

	private boolean calculatorOnline() {
		try {
			sumClient.sum(0.0, 0.0);
			return true;
		} catch (RuntimeException ex) {
			return !(ex.getCause() instanceof ConnectException);
		}
	}
}
