package calculator;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

public class StripTrailingZerosAfterTheDot extends TypeAdapter<BigDecimal> {

	@Override
	public void write(JsonWriter out, BigDecimal value) throws IOException {
		out.jsonValue(value.stripTrailingZeros().toPlainString());
	}

	@Override
	public BigDecimal read(JsonReader in) {
		throw new RuntimeException("Should not be called");
	}
}
