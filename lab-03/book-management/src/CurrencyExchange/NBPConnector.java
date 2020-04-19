package CurrencyExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NBPConnector {
    private final String addressString = "http://api.nbp.pl/api/exchangerates/rates/A/";
    private URL url;


    public double exchangeRate(String currency) throws IOException {
        String content = connectAndGetContent(currency);
        Pattern pattern = Pattern.compile("mid\":((\\d|\\.)*)");
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()) return Double.parseDouble(matcher.group(1));
        else return -1.0;
    }



    private String connectAndGetContent(String parameter) throws IOException {
        this.url = new URL(this.addressString+parameter+"/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }

}
