package sum;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class SumClient {

	private static final MediaType JSON
			= MediaType.parse("application/json;charset=UTF-8");

	private static class Addition {
		public List<Number> summands;
	}

	private static class Sum {
		public Number result;
	}

	private final OkHttpClient client = new OkHttpClient();
	private final ObjectMapper mapper = new ObjectMapper();
	private final String url;


	SumClient(String url) {
		this.url = url;
	}

	public Number sum(Number summand1, Number summand2) {
		try {
			Addition addition = new Addition();
			addition.summands = Arrays.asList(summand1, summand2);
			String json = mapper.writeValueAsString(addition);

			RequestBody body = RequestBody.create(JSON, json);
			Request request = new Request.Builder()
					.url(url + "/operations/sum")
					.post(body)
					.build();

			Response response = client.newCall(request).execute();
			if (response.code() == 200) {
				Sum sum = mapper.readValue(response.body().string(), Sum.class);
				return sum.result;
			}
			throw new CalculatorOffline();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
