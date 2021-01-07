package ro.mta.se.lab.controller;


import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.WeatherModel;
import ro.mta.se.lab.view.TitanLogger;

import java.util.List;

public class WeatherProvider {

    private City currentCity;
    private TitanLogger titanLogger;
    private WeatherRequest weatherRequest;

    public WeatherProvider() {
        weatherRequest = new WeatherRequest();
        titanLogger = TitanLogger.getInstance();
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }


    public WeatherModel getCurrentWeather(City city) {


        titanLogger.write("WeatherProvider provided current weather for country " + city.getId() + ". ",
                2, 3);

        return null;
    }

    public List<WeatherModel> getWeekWeather(City city) {



        titanLogger.write("WeatherProvider provided 7 day forecast for country " + city.getName() + ". ",
                2, 3);
        return null;
    }


    public String getForecastByCountryId(String countryId) {
        return weatherRequest.getForecast("api.openweathermap.org/data/2.5/weather?id=" + countryId);
    }

    public String getWeekForecast(City city) {
        return weatherRequest.getForecast("api.openweathermap.org/data/2.5/onecall?lat=" + city.getLat() +
                "&lon=" + city.getLon() +  "&exclude=minutely,hourly,alerts&units=metric");
    }


}
