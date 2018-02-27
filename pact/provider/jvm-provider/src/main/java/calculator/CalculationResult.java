package calculator;

import com.google.gson.annotations.JsonAdapter;

public class CalculationResult {
	@JsonAdapter(StripTrailingZerosAfterTheDot.class)
	public Number result;
}
