package ro.mta.se.lab.controller;


import ro.mta.se.lab.view.TitanLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRequestController {

    private static final String APIKEY = "67ca8d4acae59d540ea421e817caf1bb";

    public WeatherRequestController() {
    }

    public String getForecastByCountryId(String countryId) {
        return getForecast("api.openweathermap.org/data/2.5/weather?id=" + countryId);
    }

    public String getForecast(String targetUri) {
        try {
            URL url = new URL("http://" + targetUri  + "&appid=" + APIKEY);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.connect();
            StringBuilder response = new StringBuilder();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            return response.toString();
        }
        catch (Exception exception) {
            TitanLogger.getInstance().write(exception.getMessage(), 2, 1);
        }
        return null;
    }

}
