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

    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private static class Addition {
        public List<Double> summands;
    }

    private static class Sum {
        public Double result;
    }

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String url;


    SumClient(String url) {
        this.url = url;
    }

    public Double sum(Double summand1, Double summand2) {
        try {
            Addition addition = new Addition();
            addition.summands = Arrays.asList(summand1, summand2);
            String json = mapper.writeValueAsString(addition);

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                .url(url + "/basic/addition")
                .post(body)
                .build();

            Response response = client.newCall(request).execute();
            if (response.code() == 503) {
                throw new CalculatorOffline();
            }

            Sum sum = mapper.readValue(response.body().string(), Sum.class);
            return sum.result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
