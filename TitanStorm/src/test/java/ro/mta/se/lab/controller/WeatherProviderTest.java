package ro.mta.se.lab.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.WeatherModel;
import ro.mta.se.lab.view.TitanLogger;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherProviderTest {

    private static final String responseJson = "{\"lat\":44.49,\"lon\":26.1734,\"timezone\":\"Europe/Bucharest\",\"timezone_offset\":7200,\"current\":{\"dt\":1610451075,\"sunrise\":1610430579,\"sunset\":1610463429,\"temp\":1.34,\"feels_like\":-3.84,\"pressure\":1013,\"humidity\":93,\"dew_point\":0.33,\"uvi\":0.14,\"clouds\":90,\"visibility\":2900,\"wind_speed\":4.63,\"wind_deg\":30,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"},{\"id\":701,\"main\":\"Mist\",\"description\":\"mist\",\"icon\":\"50d\"}],\"rain\":{\"1h\":0.43}},\"daily\":[{\"dt\":1610445600,\"sunrise\":1610430579,\"sunset\":1610463429,\"temp\":{\"day\":1.25,\"min\":0.06,\"max\":3.04,\"night\":3,\"eve\":2.52,\"morn\":0.79},\"feels_like\":{\"day\":-5.12,\"night\":-1.48,\"eve\":-3.15,\"morn\":-5.25},\"pressure\":1014,\"humidity\":92,\"dew_point\":0.12,\"wind_speed\":6.29,\"wind_deg\":54,\"weather\":[{\"id\":616,\"main\":\"Snow\",\"description\":\"rain and snow\",\"icon\":\"13d\"}],\"clouds\":100,\"pop\":1,\"rain\":7.72,\"snow\":0.21,\"uvi\":0.21},{\"dt\":1610532000,\"sunrise\":1610516953,\"sunset\":1610549901,\"temp\":{\"day\":4.96,\"min\":1.86,\"max\":5.49,\"night\":1.86,\"eve\":3.95,\"morn\":3.16},\"feels_like\":{\"day\":-0.05,\"night\":-0.86,\"eve\":0.42,\"morn\":0.97},\"pressure\":1007,\"humidity\":76,\"dew_point\":1.24,\"wind_speed\":4.55,\"wind_deg\":260,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":97,\"pop\":1,\"rain\":2.24,\"uvi\":0.94},{\"dt\":1610618400,\"sunrise\":1610603325,\"sunset\":1610636374,\"temp\":{\"day\":1.84,\"min\":-1.06,\"max\":3.16,\"night\":0.28,\"eve\":1.24,\"morn\":-0.72},\"feels_like\":{\"day\":-1.94,\"night\":-4.68,\"eve\":-2.93,\"morn\":-5.4},\"pressure\":1009,\"humidity\":79,\"dew_point\":-4.97,\"wind_speed\":2.28,\"wind_deg\":250,\"weather\":[{\"id\":601,\"main\":\"Snow\",\"description\":\"snow\",\"icon\":\"13d\"}],\"clouds\":99,\"pop\":0.56,\"snow\":1.31,\"uvi\":0.82},{\"dt\":1610704800,\"sunrise\":1610689694,\"sunset\":1610722847,\"temp\":{\"day\":1.33,\"min\":-1.16,\"max\":2.92,\"night\":-0.97,\"eve\":0.94,\"morn\":-1.16},\"feels_like\":{\"day\":-2.84,\"night\":-4.13,\"eve\":-1.56,\"morn\":-5.31},\"pressure\":1012,\"humidity\":79,\"dew_point\":-7.05,\"wind_speed\":2.75,\"wind_deg\":259,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":5,\"pop\":0,\"uvi\":1.07},{\"dt\":1610791200,\"sunrise\":1610776061,\"sunset\":1610809322,\"temp\":{\"day\":-1.55,\"min\":-3.97,\"max\":-1.45,\"night\":-3.97,\"eve\":-2.67,\"morn\":-1.71},\"feels_like\":{\"day\":-7.09,\"night\":-9.59,\"eve\":-7.45,\"morn\":-6.18},\"pressure\":1019,\"humidity\":92,\"dew_point\":-5.49,\"wind_speed\":4.57,\"wind_deg\":68,\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":99,\"pop\":0.35,\"snow\":0.44,\"uvi\":0.83},{\"dt\":1610877600,\"sunrise\":1610862425,\"sunset\":1610895798,\"temp\":{\"day\":-4.49,\"min\":-5.55,\"max\":-3.3,\"night\":-3.88,\"eve\":-3.85,\"morn\":-5.24},\"feels_like\":{\"day\":-8.23,\"night\":-8.02,\"eve\":-8.1,\"morn\":-10.25},\"pressure\":1023,\"humidity\":86,\"dew_point\":-11.28,\"wind_speed\":1.4,\"wind_deg\":342,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":96,\"pop\":0.34,\"uvi\":1},{\"dt\":1610964000,\"sunrise\":1610948788,\"sunset\":1610982275,\"temp\":{\"day\":-2.84,\"min\":-5.22,\"max\":-1.15,\"night\":-2.92,\"eve\":-2.25,\"morn\":-5.07},\"feels_like\":{\"day\":-6.78,\"night\":-7.09,\"eve\":-6.04,\"morn\":-8.69},\"pressure\":1028,\"humidity\":83,\"dew_point\":-11.33,\"wind_speed\":1.85,\"wind_deg\":246,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":1},{\"dt\":1611050400,\"sunrise\":1611035148,\"sunset\":1611068753,\"temp\":{\"day\":-1.32,\"min\":-3.94,\"max\":-0.03,\"night\":-1.5,\"eve\":-1.18,\"morn\":-3.7},\"feels_like\":{\"day\":-6.95,\"night\":-6.57,\"eve\":-6.29,\"morn\":-8.55},\"pressure\":1028,\"humidity\":83,\"dew_point\":-10.35,\"wind_speed\":4.5,\"wind_deg\":242,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":1}]}";

    @Spy
    TitanLogger loggerSpy;

    @Mock
    WeatherRequest weatherRequest;

    @Mock
    City city;

    @InjectMocks
    WeatherProvider weatherProvider;

    @Before
    public void setUp() {
        when(weatherRequest.getForecast(anyString())).thenReturn(responseJson);
        when(city.getLat()).thenReturn("13.77436");
        when(city.getLon()).thenReturn("44.441959");
        when(city.getName()).thenReturn("Dawran");
    }

    @Test
    public void getWeekForecast() {
        assertEquals(weatherProvider.getWeekForecast(city), responseJson);
    }

    @Test
    public void getWeekWeather() {
        assertNotNull(weatherProvider.getWeekWeather(city));
        //Make sure we have 8 elements
        assertEquals(weatherProvider.getForecastList().size(), 8);
        assertEquals(weatherProvider.getForecastList().get(0).getTimeNow().getClass(), Date.class);
        assertNotNull(weatherProvider.getForecastList().get(0).toString());
        //Call logger at least 1 time and verify response.
        verify(loggerSpy, atLeast(1)).write(anyString(), anyInt(), anyInt());
    }

    @Test
    public void convertToF() {
        //I know for sure that 0 celsius is 32 fahrenheit with .0 as it was set
        assertEquals(weatherProvider.convertToF("0"), "32.0");
        assertEquals(weatherProvider.convertToF("0.5"), "32.9");
        assertEquals(weatherProvider.convertToF("1050.5"), "1922.9");
        assertEquals(weatherProvider.convertToF("-5"), "23.0");
        assertEquals(weatherProvider.convertToF("-102"), "-151.6");
    }

    @Test
    public void getForecastList() {
        List<WeatherModel> weatherList = weatherProvider.getWeekWeather(city);
        assertSame(weatherList, weatherProvider.getForecastList());
        //7 day forecast ahead + current day .get(0)
        assertEquals(weatherList.size(), 8);
        //Make sure we do not have errors inside any element
        for(WeatherModel mode : weatherList) {
            assertNotNull(mode.toString());
        }
    }
}
