package calculator.power;

import calculator.OnlineStatus;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeletePowerHandler implements Route {
	private OnlineStatus onlineStatus;

	public DeletePowerHandler(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	@Override
	public Object handle(Request request, Response response) {
		onlineStatus.powerOff();

		response.status(200);
		response.type("application/json");
		PowerStatus status = new PowerStatus();
		status.status = "off";
		return status;
	}
}
