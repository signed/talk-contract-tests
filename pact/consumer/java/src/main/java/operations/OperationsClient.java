package operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;

class OperationsClient {

	public static class CalculatorStatus{
		public String status;
	}

	private final OkHttpClient client = new OkHttpClient();
	private final ObjectMapper mapper = new ObjectMapper();
	private final String url;

	OperationsClient(String url) {
		this.url = url;
	}

	public CalculatorStatus powerDown() {
		try {
			okhttp3.Request request = new okhttp3.Request.Builder()
					.url(url + "/calculator/power")
					.delete()
					.build();
			Response response = client.newCall(request).execute();


			CalculatorStatus calculatorStatus = mapper.readValue(response.body().string(), CalculatorStatus.class);

			CalculatorStatus status = new CalculatorStatus();
			status.status = "off";
			return calculatorStatus;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
