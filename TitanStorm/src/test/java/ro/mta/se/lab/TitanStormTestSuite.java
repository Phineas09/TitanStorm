package ro.mta.se.lab;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ro.mta.se.lab.controller.WeatherProviderTest;
import ro.mta.se.lab.controller.WeatherRequestTest;
import ro.mta.se.lab.model.WeatherModelTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WeatherRequestTest.class,
        WeatherProviderTest.class,
        WeatherModelTest.class})
/**
 * Runs the test suite for the TitanStorm app
 */
public class TitanStormTestSuite {
}
