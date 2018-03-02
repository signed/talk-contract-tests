package calculator;

import com.google.gson.Gson;
import spark.Spark;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public class Calculator {

	public static void main(String[] args) {
		new Calculator(9090, new AlwaysOnline()).start();
	}

	public static Calculator onRandomPort(OnlineStatus onlineStatus) {
		return new Calculator(0, onlineStatus);
	}

	private final int port;
	private final OnlineStatus onlineStatus;

	private Calculator(int port, OnlineStatus onlineStatus) {
		this.port = port;
		this.onlineStatus = onlineStatus;
	}

	/**
	 * http://sparkjava.com/documentation#getting-started
	 */
	public void start() {
		Gson gson = new Gson();
		Spark.port(port);
		Spark.post("/operations/sum", "application/json", (req, res) -> {
			AdditionInput additionInput = gson.fromJson(req.body(), AdditionInput.class);
			BigDecimal sum = sumFor(additionInput);

			res.type("application/json;charset=utf-8");
			CalculationResult result = new CalculationResult();
			result.result = sum;
			return result;
		}, new JsonTransformer());
		Spark.awaitInitialization();
	}

	private BigDecimal sumFor(AdditionInput additionInput) {
		return (BigDecimal) additionInput.summands.stream().reduce(BigDecimal.ZERO, new BinaryOperator<Number>() {
					@Override
					public Number apply(Number number, Number number2) {
						return ((BigDecimal) number).add(BigDecimal.valueOf(number2.doubleValue()));
					}
				});
	}

	public int port() {
		return Spark.port();
	}

	public void shutdown() {
		Spark.stop();
	}

}
