package ro.mta.se.lab.controller;

import ro.mta.se.lab.controller.exceptions.HttpException;
import ro.mta.se.lab.controller.exceptions.TitanException;
import ro.mta.se.lab.view.TitanLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Class responsible for all the HTTP requests made.
 */
public class WeatherRequest {

    /**
     * Static api keys for the used api s
     */
    private static final String APIKEY = "67ca8d4acae59d540ea421e817caf1bb";
    private static final String GEOLOCATIONAPIKEY = "at_dSkpqzlKeSA4KaW2PCdTOkHE7eF93";

    public WeatherRequest() {
    }

    /**
     * Special function to get the forecast for one week, it will auto append the APIKEY
     * and the protocol used to access the resource.
     * @param targetUri the url target with custom params
     * @return response json as string.
     * @see #makeHttpRequest
     */
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

    /**
     * Function that is the core for the class, will make all the GET requests
     * and only accept json as a response. <p>
     * Will log any request made.
     * @param targetUri target url with protocol and keys if requested
     * @return response json as string.
     * @throws TitanException if and exception occurs
     */
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
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
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

    public String getWeatherKey() {
        return APIKEY;
    }
}
