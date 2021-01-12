package ro.mta.se.lab.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.WeatherModel;
import ro.mta.se.lab.view.TitanLogger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WeatherProvider {

    private TitanLogger titanLogger;
    private WeatherRequest weatherRequest;
    private List<WeatherModel> forecastList;

    public WeatherProvider() {
        weatherRequest = new WeatherRequest();
        titanLogger = TitanLogger.getInstance();
        forecastList = new ArrayList<>();
    }

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

    public String getWeekForecast(City city) {
        return weatherRequest.getForecast("api.openweathermap.org/data/2.5/onecall?lat=" + city.getLat() +
                "&lon=" + city.getLon() +  "&exclude=minutely,hourly,alerts&units=metric");
    }

    public String convertToF(String celsiusTemp) {
        DecimalFormat df = new DecimalFormat("0.0");
        Double degrees = Double.parseDouble(celsiusTemp);
        degrees = ((degrees*9)/5)+32;
        return df.format(degrees);
    }

    public List<WeatherModel> getForecastList() {
        return forecastList;
    }
}
