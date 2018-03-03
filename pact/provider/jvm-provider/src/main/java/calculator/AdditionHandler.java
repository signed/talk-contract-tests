package calculator;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

class AdditionHandler implements Route {
	private final OnlineStatus onlineStatus;
	private final Gson gson;

	public AdditionHandler(OnlineStatus onlineStatus, Gson gson) {
		this.onlineStatus = onlineStatus;
		this.gson = gson;
	}

	@Override
	public Object handle(Request req, Response res) {
		if (onlineStatus.isOffline()) {
			res.status(503);
			return "";
		}
		AdditionInput additionInput = gson.fromJson(req.body(), AdditionInput.class);
		BigDecimal sum = sumFor(additionInput);

		res.type("application/json;charset=utf-8");
		CalculationResult result = new CalculationResult();
		result.result = sum;
		return result;
	}

	private BigDecimal sumFor(AdditionInput additionInput) {
		return (BigDecimal) additionInput.summands.stream().reduce(BigDecimal.ZERO, new BinaryOperator<Number>() {
			@Override
			public Number apply(Number number, Number number2) {
				return ((BigDecimal) number).add(BigDecimal.valueOf(number2.doubleValue()));
			}
		});
	}

}
