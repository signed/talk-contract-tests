package calculator;

import com.google.gson.Gson;
import spark.Service;

public class CalculatorService {

	public static void main(String[] args) {
		System.out.println("Started calculator");
		new CalculatorService(9090, new DefaultOnlineStatus()).start();
	}

	public static CalculatorService onRandomPort(OnlineStatus onlineStatus) {
		return new CalculatorService(0, onlineStatus);
	}

	private final int port;
	private final OnlineStatus onlineStatus;
	private final Gson gson = new Gson();
	private Service http;

	private CalculatorService(int port, OnlineStatus onlineStatus) {
		this.port = port;
		this.onlineStatus = onlineStatus;
	}

	public static class PowerStatus{
		public String status;
	}

	/**
	 * http://sparkjava.com/documentation#getting-started
	 */
	public void start() {
		http = Service.ignite().port(port);

		http.post("/operations/sum", "application/json", new SumHandler(onlineStatus, gson), new JsonTransformer());
		http.delete("/calculator/power", new DeletePowerHandler(onlineStatus), new JsonTransformer());
		http.awaitInitialization();
	}

	public int port() {
		return http.port();
	}

	public void shutdown() {
		try {
			http.stop();
			Thread.sleep(500);
			http = null;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
