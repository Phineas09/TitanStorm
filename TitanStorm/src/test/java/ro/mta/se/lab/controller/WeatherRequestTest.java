package ro.mta.se.lab.controller;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.localserver.LocalTestServer;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.*;
import ro.mta.se.lab.controller.exceptions.TitanException;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test class for WeatherRequest, it's a unit test with no mocks, it has a localserver for testing
 * the requests responses codes and messages.
 */
public class WeatherRequestTest {

    private WeatherRequest weatherRequest;

    private static final String APIKEY = "67ca8d4acae59d540ea421e817caf1bb";
    private static final String GEOLOCATIONAPIKEY = "at_dSkpqzlKeSA4KaW2PCdTOkHE7eF93";

    /**
     * Local server for testing connectivity and what will the function return.
     */
    private LocalTestServer server = new LocalTestServer(null, null);

    private HttpRequestHandler myHttpRequestHandler = new HttpRequestHandler() {
        @Override
        public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
            response.setEntity(new StringEntity("testHttpRequest"));
        }
    };

    @Before
    public void setUp() throws Exception {
        weatherRequest = new WeatherRequest();

        //Start the local server and register all paths as valid
        server.start();
        server.register("/*", myHttpRequestHandler);
    }

    @After
    public void tearDown() throws Exception {
        //Stop the server, the server it's not static so i cannot do an @AfterClass
        server.stop();
    }

    @Test
    public void testGetApiKeys() {
        //Test the api keys are OK
        assertEquals(weatherRequest.getGeoApiKey(), GEOLOCATIONAPIKEY);
        assertEquals(weatherRequest.getWeatherKey(), APIKEY);
    }

    /**
     * Tests the responses provided and expected
     * @throws Exception in case the test fails
     */
    @Test
    public void testMakeHttpRequest() throws Exception {
        String serverUrl = "http:/" + server.getServiceAddress();
        assertEquals("testHttpRequest", weatherRequest.makeHttpRequest(serverUrl));
        assertEquals("testHttpRequest/test/randomString", weatherRequest.makeHttpRequest(serverUrl) + "/test/randomString");
        assertEquals("testHttpRequest?apikey=" + weatherRequest.getGeoApiKey(), weatherRequest.makeHttpRequest(serverUrl) + "?apikey=" + weatherRequest.getGeoApiKey());
    }

    /**
     * Tests that the application will throw given a wrong url as target
     */
    @Test (expected = TitanException.class)
    public void testMakeHttpRequestThrow() throws TitanException {
        weatherRequest.makeHttpRequest("");
    }

    /**
     * Test the connectivity with the real used api.
     */
    @Test (timeout = 450)
    public void testGetForecast() {
        //Request directly on the website
        String requestStringFirst = "api.openweathermap.org/data/2.5/onecall?lat=" + "44.640041" +
                "&lon=" + "26.215851" +  "&exclude=minutely,hourly,alerts&units=metric";

        String requestStringSecond = "api.openweathermap.org/data/2.5/onecall?lat=&exclude=minutely,hourly,alerts&units=metric";

        assertNotNull(weatherRequest.getForecast(requestStringFirst));
        assertNull(weatherRequest.getForecast(requestStringSecond));
        assertNull(weatherRequest.getForecast("aprmap.org"));
    }
}
