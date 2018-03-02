package calculator;

import spark.Request;
import spark.Response;
import spark.Route;

class DeletePowerHandler implements Route {
	private OnlineStatus onlineStatus;

	DeletePowerHandler(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	@Override
	public Object handle(Request request, Response response) {
		onlineStatus.powerOff();

		response.status(200);
		response.type("application/json");
		CalculatorService.PowerStatus status = new CalculatorService.PowerStatus();
		status.status = "off";
		return status;
	}
}
