package ro.mta.se.lab.model;

import org.apache.commons.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.view.TitanLogger;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Model for the weather forecast, holds all the data that is provided by the api
 * It will parse the json obtained into the afferent members.
 */
public class WeatherModel {

    private TitanLogger titanLogger;

    private Date timeNow;
    private Date sunRise;
    private Date sunSet;
    private String currentTempC;
    private String feelsLikeC;
    private String pressure;
    private String humidity;
    private String dewPoint;
    private String uvIndex;
    private String clouds;
    private String windSpeed;
    private String windDeg;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;

    //Only for the daily weatherForecast
    private String minTemp;
    private String maxTemp;

    /**
     * Constructor to build a WeatherModel from a jsonObject
     * all members are self explanatory
     * @param jsonType 0 for current and 1 for daily parsing
     * @param jsonObject the jsonObject that will be parsed into the model
     */
    public WeatherModel(int jsonType, JSONObject jsonObject) {
        titanLogger = TitanLogger.getInstance();
        try {
            parseJson(jsonType, jsonObject);

        } catch (Exception e) {
            titanLogger.write(e.getMessage(), 2, 1);
        }
    }

    /**
     * Parses the json into members.
     *
     * @param jsonType 0 for current and 1 for daily parsing
     * @param jsonObject the jsonObject that will be parsed into the model
     * @throws Exception if the json is not formatted as expected.
     */
    private void parseJson(int jsonType, JSONObject jsonObject) throws Exception {

        timeNow = new Date(Long.parseLong(jsonObject.get("dt").toString() + "000"));
        sunRise = new Date(Long.parseLong(jsonObject.get("sunrise").toString() + "000"));
        sunSet = new Date(Long.parseLong(jsonObject.get("sunset").toString() + "000"));

        pressure = jsonObject.get("pressure").toString();
        humidity = jsonObject.get("humidity").toString();
        dewPoint = jsonObject.get("dew_point").toString();
        uvIndex = jsonObject.get("uvi").toString();
        clouds = jsonObject.get("clouds").toString();
        windSpeed = jsonObject.get("wind_speed").toString();
        windDeg = jsonObject.get("wind_deg").toString();

        //Weather
        JSONObject weatherJson = (JSONObject)((JSONArray)jsonObject.get("weather")).get(0);

        weatherMain = weatherJson.getString("main");
        weatherDescription = WordUtils.capitalize(weatherJson.getString("description"));
        weatherIcon = weatherJson.getString("icon");

        if(jsonType == 0) {
            currentTempC = jsonObject.get("temp").toString();
            feelsLikeC = jsonObject.get("feels_like").toString();

        } else {
            currentTempC = ((JSONObject)jsonObject.get("temp")).get("day").toString();
            //TODO: Get min and max
            minTemp = ((JSONObject)jsonObject.get("temp")).get("min").toString();
            maxTemp = ((JSONObject)jsonObject.get("temp")).get("max").toString();
            feelsLikeC = ((JSONObject)jsonObject.get("feels_like")).get("day").toString();
        }

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
     * Those are only used for testing !
     */
    public WeatherModel() {
        titanLogger = TitanLogger.getInstance();
    }

    /**
     * Sets the weather model for the class.
     * @param jsonType 0 for current and 1 for daily parsing
     * @param jsonObject the jsonObject that will be parsed into the model
     */
    public void setWeatherModelData(int jsonType, JSONObject jsonObject) {
        try {
            parseJson(jsonType, jsonObject);
        } catch (Exception e) {
            titanLogger.write(e.getMessage(), 2, 1);
        }
    }

    //Setters and getters

    public Date getTimeNow() {
        return timeNow;
    }

    public Date getSunRise() {
        return sunRise;
    }

    public Date getSunSet() {
        return sunSet;
    }

    public String getCurrentTempC() {
        return currentTempC;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public String getClouds() {
        return clouds;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "timeNow=" + timeNow +
                ", sunRise=" + sunRise +
                ", sunSet=" + sunSet +
                ", currentTempC='" + currentTempC + '\'' +
                ", feelsLikeC='" + feelsLikeC + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", dewPoint='" + dewPoint + '\'' +
                ", uvIndex='" + uvIndex + '\'' +
                ", clouds='" + clouds + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDeg='" + windDeg + '\'' +
                ", weatherMain='" + weatherMain + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                '}';
    }
}
