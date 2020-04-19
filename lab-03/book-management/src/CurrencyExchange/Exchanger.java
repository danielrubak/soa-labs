package CurrencyExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Exchanger
{
    NBPConnector nbpConnector = new NBPConnector();
    Map<String,Double> ratesMap = new HashMap<>();

    public Exchanger() {
        try {
            ratesMap.put("EUR",nbpConnector.exchangeRate("EUR"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ratesMap.put("PLN",1.0);
        System.out.println(ratesMap);
    }

    public double exchangeToPLN(String currency, double value) {
        if(ratesMap.containsKey(currency)) return ratesMap.get(currency)*value;
        else {
            double rate = 0;
            try {
                rate = nbpConnector.exchangeRate(currency);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ratesMap.put(currency,rate);
            return rate;
        }
    }
}
