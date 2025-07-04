package com.example.exchangerates;

import java.util.HashMap;
import java.util.Map;

public class ExchangeService {
    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD_TO_INR", 83.2);
        exchangeRates.put("INR_TO_USD", 0.012);
        exchangeRates.put("EUR_TO_USD", 1.07);
    }

    public static double getRate(String from, String to) {
        return exchangeRates.getOrDefault(from.toUpperCase() + "_TO_" + to.toUpperCase(), -1.0);
    }

    public static double convert(String from, String to, double amount) {
        double rate = getRate(from, to);
        if (rate == -1.0) throw new IllegalArgumentException("Rate not found");
        return rate * amount;
    }
}
