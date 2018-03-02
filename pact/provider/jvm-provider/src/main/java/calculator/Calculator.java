package calculator;

import com.google.gson.Gson;
import spark.Service;
import spark.Spark;

public class Calculator {

	public static void main(String[] args) {
		new Calculator(9090, new AlwaysOnline()).start();
	}

	public static Calculator onRandomPort(OnlineStatus onlineStatus) {
		return new Calculator(0, onlineStatus);
	}


	private final int port;
	private final OnlineStatus onlineStatus;
	private final Gson gson = new Gson();
	private Service http;

	private Calculator(int port, OnlineStatus onlineStatus) {
		this.port = port;
		this.onlineStatus = onlineStatus;
	}

	/**
	 * http://sparkjava.com/documentation#getting-started
	 */
	public void start() {
		http = Service.ignite().port(port);

		http.post("/operations/sum", "application/json", new SumHandler(onlineStatus, gson), new JsonTransformer());
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
