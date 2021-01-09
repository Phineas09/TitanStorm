package ro.mta.se.lab.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.Main;
import ro.mta.se.lab.controller.TitanController;
import ro.mta.se.lab.model.City;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TitanScene {

    private static TitanScene instance = null;
    private static Scene scene = null;
    private static Stage bigStage = null;
    private static final String ScenePath = "view/TitanScene";


    private TitanScene(String scenePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(scenePath + ".fxml"));

            HashMap<String, ArrayList<City>> countryList =
                    createCountryList("src/main/resources/ro/mta/se/lab/model/cityList.txt");

            fxmlLoader.setController(new TitanController(countryList));
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Main.class.getResource("view/styles.css").toExternalForm());

        } catch (IOException exception) {
            TitanLogger.getInstance().write(exception.getMessage(), 2, 1);
        }
    }

    public static TitanScene getInstance() {
        if (instance == null) {
            instance = new TitanScene(ScenePath);
        }
        return instance;
    }

    public static TitanScene getInstance(Stage stage) {
        if (instance == null) {
            instance = new TitanScene(ScenePath);
        }
        bigStage = stage;
        stage.setScene(scene);
        stage.setTitle("TitanStorm Weather App");
        stage.setResizable(false);
        stage.show();
        return instance;
    }

    public void changePrimaryColor(String color) {

        String image = Main.class.getResource("backgrounds/day_clearsky.png").toExternalForm();
        bigStage.getScene().getRoot().setStyle(
                "-fx-background-image: url('" + image + "'); " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: cover;" +
                "-fx-background-position: center center;" +
                "-fx-text-background-color: white;"
        );

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private HashMap<String, ArrayList<City>> createCountryList(String filePath) {

        HashMap<String, ArrayList<City>> countryHashMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
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
