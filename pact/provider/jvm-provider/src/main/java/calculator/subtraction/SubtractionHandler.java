package calculator.subtraction;

import calculator.OnlineStatus;
import calculator.addition.AdditionInput;
import calculator.addition.CalculationResult;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public class SubtractionHandler implements Route {
    private final OnlineStatus onlineStatus;
    private final Gson gson;

    public SubtractionHandler(OnlineStatus onlineStatus, Gson gson) {
        this.onlineStatus = onlineStatus;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        SubtractionInput subtractionInput = parsedInputFrom(request);
        response.type("application/json;charset=utf-8");
        CalculationResult result = new CalculationResult();
        result.result = subtract(subtractionInput);
        return result;
    }

    private SubtractionInput parsedInputFrom(Request request) {
        return gson.fromJson(request.body(), SubtractionInput.class);
    }

    private BigDecimal subtract(SubtractionInput subtractionInput) {
        return subtractionInput.subtrahends.stream().reduce(subtractionInput.minuend, BigDecimal::subtract);
    }
}
