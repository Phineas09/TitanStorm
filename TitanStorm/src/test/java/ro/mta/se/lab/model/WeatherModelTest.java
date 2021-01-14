package ro.mta.se.lab.model;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ro.mta.se.lab.view.TitanLogger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test class for WeatherModel will mock the logger for detecting crashes inside the constructor
 * and will test the behaviour of the constructor, unwrapping a json into the correct variables
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherModelTest {

    static String jsonCurrentString = "{\"dt\":1610451075,\"sunrise\":1610430579,\"sunset\":1610463429,\"temp\":1.34,\"feels_like\":-3.84,\"pressure\":1013,\"humidity\":93,\"dew_point\":0.33,\"uvi\":0.14,\"clouds\":90,\"visibility\":2900,\"wind_speed\":4.63,\"wind_deg\":30,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"},{\"id\":701,\"main\":\"Mist\",\"description\":\"mist\",\"icon\":\"50d\"}],\"rain\":{\"1h\":0.43}}";
    static String jsonDailyString = "{\"dt\":1610445600,\"sunrise\":1610430579,\"sunset\":1610463429,\"temp\":{\"day\":1.25,\"min\":0.06,\"max\":3.04,\"night\":3,\"eve\":2.52,\"morn\":0.79},\"feels_like\":{\"day\":-5.12,\"night\":-1.48,\"eve\":-3.15,\"morn\":-5.25},\"pressure\":1014,\"humidity\":92,\"dew_point\":0.12,\"wind_speed\":6.29,\"wind_deg\":54,\"weather\":[{\"id\":616,\"main\":\"Snow\",\"description\":\"rain and snow\",\"icon\":\"13d\"}],\"clouds\":100,\"pop\":1,\"rain\":7.72,\"snow\":0.21,\"uvi\":0.21}";

    @Spy
    TitanLogger titanLogger;

    @InjectMocks
    WeatherModel weatherModel;

    @Test
    public void testErrorLoggingConstructor() {
        //This should generate error
        weatherModel.setWeatherModelData(1, new JSONObject("{}"));
        verify(titanLogger, atLeast(1)).write(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testHealthyConstructorCurrent() {
        //This should generate error
        //I know the values from jsonDailyString
        weatherModel.setWeatherModelData(0, new JSONObject(jsonCurrentString));
        assertEquals(weatherModel.getClouds(), "90");
        assertEquals(weatherModel.getWindSpeed(), "4.63");
        assertEquals(weatherModel.getWindDeg(), "30");
        assertEquals(weatherModel.getWeatherIcon(), "10d");
        assertEquals(weatherModel.getWeatherMain(), "Rain");
        assertEquals(weatherModel.getFeelsLikeC(), "-3.84");
        assertEquals(weatherModel.getCurrentTempC(), "1.34");
        assertEquals(weatherModel.getPressure(), "1013");
        verify(titanLogger, never()).write(anyString(), anyInt(), anyInt());

    }

    @Test
    public void testHealthyConstructorDaily() {
        //This should generate error
        //I know the values from jsonDailyString
        weatherModel.setWeatherModelData(1, new JSONObject(jsonDailyString));
        assertEquals(weatherModel.getClouds(), "100");
        assertEquals(weatherModel.getWindSpeed(), "6.29");
        assertEquals(weatherModel.getWindDeg(), "54");
        assertEquals(weatherModel.getWeatherIcon(), "13d");
        assertEquals(weatherModel.getWeatherMain(), "Snow");
        assertEquals(weatherModel.getFeelsLikeC(), "-5.12");
        assertEquals(weatherModel.getHumidity(), "92");
        assertEquals(weatherModel.getPressure(), "1014");
        verify(titanLogger, never()).write(anyString(), anyInt(), anyInt());
    }

}
