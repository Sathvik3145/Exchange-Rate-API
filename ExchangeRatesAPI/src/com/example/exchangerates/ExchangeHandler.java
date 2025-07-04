package com.example.exchangerates;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;

public class ExchangeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        Map<String, String> queryParams = QueryParser.parse(uri.getQuery());

        String response = "";
        int statusCode = 200;

        try {
            String from = queryParams.get("from");
            String to = queryParams.get("to");

            if (path.contains("/rate")) {
                double rate = ExchangeService.getRate(from, to);
                response = String.valueOf(rate);
            } else if (path.contains("/convert")) {
                double amount = Double.parseDouble(queryParams.get("amount"));
                double converted = ExchangeService.convert(from, to, amount);
                response = String.valueOf(converted);
            } else {
                statusCode = 404;
                response = "Endpoint not found";
            }
        } catch (Exception e) {
            statusCode = 400;
            response = "Error: " + e.getMessage();
        }

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
