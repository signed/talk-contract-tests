package calculator;

import calculator.addition.AdditionHandler;
import calculator.power.DeletePowerHandler;
import com.google.gson.Gson;
import spark.Service;

public class CalculatorEndpoints {

	private final Gson gson = new Gson();
	private final OnlineStatus onlineStatus;

	CalculatorEndpoints(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public void register(Service http) {
		http.post("/basic/addition", "application/json", new AdditionHandler(onlineStatus, gson), new JsonTransformer());
		http.delete("/calculator/power", new DeletePowerHandler(onlineStatus), new JsonTransformer());
	}
}
