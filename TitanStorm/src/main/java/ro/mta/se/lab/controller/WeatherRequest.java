package ro.mta.se.lab.controller;

import ro.mta.se.lab.controller.exceptions.HttpException;
import ro.mta.se.lab.controller.exceptions.TitanException;
import ro.mta.se.lab.view.TitanLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRequest {

    private static final String APIKEY = "67ca8d4acae59d540ea421e817caf1bb";
    private static final String GEOLOCATIONAPIKEY = "at_dSkpqzlKeSA4KaW2PCdTOkHE7eF93";

    public WeatherRequest() {
    }

    public String getForecast(String targetUri) {
        try {
            //System.out.println("http://" + targetUri  + "&appid=" + APIKEY);
            return makeHttpRequest("http://" + targetUri  + "&appid=" + APIKEY);
        }
        catch (Exception exception) {
            TitanLogger.getInstance().write(exception.getMessage(), 2, 1);
        }
        return null;
    }


    public String makeHttpRequest(String targetUri) throws TitanException {
        try {
            TitanLogger.getInstance().write("HTTP request to " + targetUri, 2, 3);
            URL url = new URL(targetUri);
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
            throw new HttpException(exception.getMessage());
        }
    }

    public String getGeoApiKey() {
        return GEOLOCATIONAPIKEY;
    }
}
