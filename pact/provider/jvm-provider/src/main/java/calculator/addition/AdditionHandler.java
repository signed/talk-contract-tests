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
	public Object handle(Request request, Response response) {
		if (onlineStatus.isOffline()) {
			response.status(503);
			return "";
		}

        response.type("application/json;charset=utf-8");
        CalculationResult result = new CalculationResult();
        result.result = sumFor(parsedInputFrom(request));
        return result;
    }

    private AdditionInput parsedInputFrom(Request request) {
        return gson.fromJson(request.body(), AdditionInput.class);
    }

    private BigDecimal sumFor(AdditionInput additionInput) {
		return additionInput.summands.stream().reduce(ZERO, BigDecimal::add);
	}

}
