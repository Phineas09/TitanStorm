package ro.mta.se.lab.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ro.mta.se.lab.Main;
import ro.mta.se.lab.controller.TitanController;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.WeatherModel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Static class used as view for the GUI TitanScene.fxml
 */
public class TitanScene {
    /** Instance member */
    private static TitanScene instance = null;
    /** Scene and the Stage */
    private static Scene scene = null;
    private static Stage bigStage = null;
    private static final String ScenePath = "view/TitanScene";
    /** For the visibility of the bottom grid and middle one */
    private static boolean visibleGridPanes = false;

    /**
     * Maps for background and color association of the scene root element.
     */
    private static HashMap<String, String> iconToBackgroundMap;
    private static HashMap<String, String> backgroundToColorSchemeMap;

    /**
     * Wil load the given scene file with the afferent stylesheet and controller, will also initialize the
     * hashMap needed for the controller.
     * @param scenePath Path for the scene to be loaded
     * @param countryListPath Path for the city list to be loaded
     */
    private TitanScene(String scenePath, String countryListPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(scenePath + ".fxml"));

            //String countryPath = (countryListPath == null) ? "src/main/resources/ro/mta/se/lab/model/cityList.txt" : countryListPath;

            HashMap<String, ArrayList<City>> countryList =
                    createCountryList(countryListPath);

            fxmlLoader.setController(new TitanController(countryList));
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Main.class.getResource("view/TitanSceneStyle.css").toExternalForm());

            //Initialize static mapping for the backgrounds and colors.
            iconToBackgroundMap = new HashMap<>();
            iconToBackgroundMap.put("01d", "day_clearsky");
            iconToBackgroundMap.put("02d", "day_partlycloudy");
            iconToBackgroundMap.put("03d", "day_partlycloudy");
            iconToBackgroundMap.put("04d", "day_cloudy");
            iconToBackgroundMap.put("09d", "day_rain");
            iconToBackgroundMap.put("10d", "day_rain");
            iconToBackgroundMap.put("11d", "day_rain");
            iconToBackgroundMap.put("13d", "day_snow");
            iconToBackgroundMap.put("50d", "day_fog");
            iconToBackgroundMap.put("01n", "night_clearsky");
            iconToBackgroundMap.put("02n", "night_partlycloudy");
            iconToBackgroundMap.put("03n", "night_partlycloudy");
            iconToBackgroundMap.put("04n", "night_cloudy");
            iconToBackgroundMap.put("09n", "night_rain");
            iconToBackgroundMap.put("10n", "night_rain");
            iconToBackgroundMap.put("11n", "night_rain");
            iconToBackgroundMap.put("13n", "night_snow");
            iconToBackgroundMap.put("50n", "night_rain");

            backgroundToColorSchemeMap = new HashMap<>();
            backgroundToColorSchemeMap.put("day_clearsky", "black");
            backgroundToColorSchemeMap.put("day_cloudy", "black");
            backgroundToColorSchemeMap.put("day_fog", "black");
            backgroundToColorSchemeMap.put("day_partlycloudy", "black");
            backgroundToColorSchemeMap.put("day_rain", "white");
            backgroundToColorSchemeMap.put("day_snow", "black");
            backgroundToColorSchemeMap.put("night_clearsky", "white");
            backgroundToColorSchemeMap.put("night_cloudy", "white");
            backgroundToColorSchemeMap.put("night_partlycloudy", "white");
            backgroundToColorSchemeMap.put("night_rain", "white");
            backgroundToColorSchemeMap.put("night_snow", "white");

            //Set the bottom grid, middle grid and refresh button as not visible.
            scene.lookup("#infoGridPane").setVisible(false);
            scene.lookup("#bottomGridPane").setVisible(false);
            scene.lookup("#refreshButton").setVisible(false);


        } catch (IOException exception) {
            TitanLogger.getInstance().write(exception.getMessage(), 2, 1);
        }
    }

    /**
     * Singleton method
     * @return instance of the class
     */
    public static TitanScene getInstance() {
        if (instance == null) {
            instance = new TitanScene(ScenePath, null);
        }
        return instance;
    }

    /**
     * Singleton method providing the stage.
     * @param stage stage that will be set
     * @param countryListPath Path for the city list to be loaded
     * @return instance of the class
     */
    public static TitanScene getInstance(Stage stage, String countryListPath) {
        if (instance == null) {
            instance = new TitanScene(ScenePath, countryListPath);
        }
        bigStage = stage;
        stage.setScene(scene);
        stage.setTitle("TitanStorm Weather App");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("miscellaneous/icons/appIcon.png")));
        stage.setResizable(false);
        stage.show();
        return instance;
    }

    /**
     * This function will change the root element style according the current weather type
     * @param weatherModel current weather
     */
    public void changeBackgroundAndColorScheme(WeatherModel weatherModel) {
        String image = Main.class.getResource("miscellaneous/backgrounds/" + iconToBackgroundMap.get(weatherModel.getWeatherIcon()) +".png").toExternalForm();
        bigStage.getScene().getRoot().setStyle(
                "-fx-background-image: url('" + image + "'); " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: cover;" +
                "-fx-background-position: center center;" +
                "-fx-text-background-color:" + backgroundToColorSchemeMap.get(iconToBackgroundMap.get(weatherModel.getWeatherIcon())) + ";"
        );
    }

    /**
     * Function will make the following elements visible again
     */
    public void makeGridPanesVisible() {
        if(!visibleGridPanes) {
            bigStage.getScene().lookup("#infoGridPane").setVisible(true);
            bigStage.getScene().lookup("#bottomGridPane").setVisible(true);
            bigStage.getScene().lookup("#refreshButton").setVisible(true);
            visibleGridPanes = true;
        }
    }

    /**
     * Will load the given fxml file and will return it.
     * @param fxml the fxml target name from resources
     * @return will return an FXMLLoader object for the requested target
     * @throws IOException in case of an exception
     */
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    /**
     * This function will generate a structure for the given target file, <p>
     * On each line there must be something with the given structure separated by <b>TAB</b> <p>
     *  <b>ID   CITI_NAME   LAT LONG    COUNTRY_CODE</b>
     * @param filePath of the target file with the list of city
     * @return map of the countries with the list of their cities
     */
    private HashMap<String, ArrayList<City>> createCountryList(String filePath) {

        HashMap<String, ArrayList<City>> countryHashMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cityInformation = line.split("\t");
                if (cityInformation.length == 5) {
                    ArrayList<City> countryList = countryHashMap.get(cityInformation[4]); //Get by country
                    if (countryList != null) {
                        countryList.add(new City(cityInformation[0], cityInformation[1],
                                cityInformation[2], cityInformation[3], cityInformation[4]));
                    } else {
                        ArrayList<City> list = new ArrayList<>();
                        list.add(new City(cityInformation[0], cityInformation[1],
                                cityInformation[2], cityInformation[3], cityInformation[4]));
                        countryHashMap.put(cityInformation[4], list);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            TitanLogger.getInstance().write(e.getMessage(), 2, 1);
        }
        return countryHashMap;
    }

}
