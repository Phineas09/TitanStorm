package ro.mta.se.lab.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.WeatherModel;
import ro.mta.se.lab.view.TitanLogger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Weather provider for the program, will use WeatherRequest to get responses from the api, parse the response
 * and store a 7 day forecast.
 * Will also log every request that is made.
 */
public class WeatherProvider {

    private TitanLogger titanLogger;
    private WeatherRequest weatherRequest;
    private List<WeatherModel> forecastList;

    /**
     * Basic constructor
     */
    public WeatherProvider() {
        weatherRequest = new WeatherRequest();
        titanLogger = TitanLogger.getInstance();
        forecastList = new ArrayList<>();
    }

    /**
     * Parses and returns a list of 8 elements with the forecast for 7 day plus the current day.
     * @param city target city element
     * @return list of 8 elements containing the forecast
     */
    public List<WeatherModel> getWeekWeather(City city) {

        String jsonResponse = getWeekForecast(city);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        forecastList.clear();
        try {
            forecastList.add(new WeatherModel(0, (JSONObject) jsonObject.get("current")));

            JSONArray dailyWeatherArray = (JSONArray) jsonObject.get("daily");
            dailyWeatherArray.remove(0);
            for (Object o : dailyWeatherArray) {
                JSONObject current = (JSONObject) o;
                forecastList.add(new WeatherModel(1, current));
            }
        }
        catch (Exception e) {
            titanLogger.write(e.getMessage(), 2, 1);
            return null;
        }
        titanLogger.write("WeatherProvider provided a 7 day forecast for city " + city.getName() + ". ",
                2, 3);
        return forecastList;
    }

    /**
     * Gets the response for the given city from the WeatherRequest
     * @param city target city
     * @return string of the response json
     */
    public String getWeekForecast(City city) {
        return weatherRequest.getForecast("api.openweathermap.org/data/2.5/onecall?lat=" + city.getLat() +
                "&lon=" + city.getLon() +  "&exclude=minutely,hourly,alerts&units=metric");
    }

    /**
     * Converts degrees C into F.
     *
     * @param celsiusTemp string containing a value of degrees in C
     * @return a string with one decimal with the value of degrees in F
     */
    public String convertToF(String celsiusTemp) {
        DecimalFormat df = new DecimalFormat("0.0");
        Double degrees = Double.parseDouble(celsiusTemp);
        degrees = ((degrees*9)/5)+32;
        return df.format(degrees);
    }

    /**
     * Getter for the forecast list
     * @return list of 8 elements containing the forecast
     */
    public List<WeatherModel> getForecastList() {
        return forecastList;
    }
}
