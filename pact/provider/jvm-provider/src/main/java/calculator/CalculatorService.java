package calculator;

import calculator.power.InMemoryOnlineStatus;
import spark.Service;

public class CalculatorService {

    public static void main(String[] args) {
        System.out.println("Started calculator");
        new CalculatorService(9090, new InMemoryOnlineStatus()).start();
    }

    public static CalculatorService onRandomPort(OnlineStatus onlineStatus) {
        return new CalculatorService(0, onlineStatus);
    }

    private final CalculatorEndpoints endpoints;
    private final int port;

    private Service http;

    private CalculatorService(int port, OnlineStatus onlineStatus) {
        this.port = port;
        endpoints = new CalculatorEndpoints(onlineStatus);
    }

    /**
     * http://sparkjava.com/documentation#getting-started
     */
    public void start() {
        http = Service.ignite().port(port);
        endpoints.register(http);
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
