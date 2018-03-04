package calculator.addition;

import calculator.OnlineStatus;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class AdditionHandler implements Route {
	private final OnlineStatus onlineStatus;
	private final Gson gson;

	public AdditionHandler(OnlineStatus onlineStatus, Gson gson) {
		this.onlineStatus = onlineStatus;
		this.gson = gson;
	}

	@Override
	public Object handle(Request req, Response response) {
		if (onlineStatus.isOffline()) {
			response.status(503);
			return "";
		}

        return __(response, sumFor(parsedInput(req)));
	}

    private AdditionInput parsedInput(Request req) {
        return gson.fromJson(req.body(), AdditionInput.class);
    }

    private CalculationResult __(Response res, BigDecimal sum) {
        res.type("application/json;charset=utf-8");
        CalculationResult result = new CalculationResult();
        result.result = sum;
        return result;
    }

    private BigDecimal sumFor(AdditionInput additionInput) {
		return additionInput.summands.stream().reduce(ZERO, BigDecimal::add);
	}

}
