package ro.mta.se.lab.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.view.TitanLogger;
import ro.mta.se.lab.view.TitanScene;

import javax.swing.text.IconView;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TitanController {

    private HashMap<String, ArrayList<City>> countryList;

    @FXML
    private Label countryLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private ComboBox<String> countryDropdown;

    private AutoCompleteComboBox<String> autoCompleteCountryDropdown;

    @FXML
    private ComboBox<City> cityDropdown;

    @FXML
    private ImageView currentWeatherIcon;

    @FXML
    private GridPane bottomGridPane;

    private AutoCompleteComboBox<String> autoCompleteCityDropdown;

    private WeatherProvider weatherProvider;

    public TitanController() {
    }

    public TitanController(HashMap<String, ArrayList<City>> countryList) {
        this.countryList = countryList;
        weatherProvider = new WeatherProvider();
    }

    @FXML
    private void initialize() {

        //Set test image
        File file = new File("src/main/resources/ro/mta/se/lab/icons/01d.png");
        Image image = new Image(file.toURI().toString());
        currentWeatherIcon.setImage(image);


        Set<String> sortedKeys = new TreeSet<>(countryList.keySet());
        countryDropdown.setItems(FXCollections.observableArrayList(sortedKeys));

        autoCompleteCountryDropdown = new AutoCompleteComboBox<>(countryDropdown);
        autoCompleteCityDropdown = new AutoCompleteComboBox<>(cityDropdown);


        //Maybe get local forecast
    }

    @FXML
    private void cityActionHandler(ActionEvent event) {
        try {
            threadDispatcherSetForecast(cityDropdown.getSelectionModel().getSelectedItem());
        }
        catch (Exception ex) {
        }
    }

    public void threadDispatcherSetForecast(City city) {
        //System.out.println(city.getName());

        TitanThread.runNewThread(() -> {
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("Make request");
                    //WeatherRequest weatherRequest = new WeatherRequest();
                    //WeatherRequest weatherRequest = new WeatherRequest().getWeekForecast();

                    //String jsonResponse = weatherRequest.getWeekForecast(city);


                    //Parse response
                    //System.out.println(jsonResponse);

                    //Change background images and other stuff
                    Platform.runLater(() -> {
                        clearAllSelectionsFromBottomPane();
                    });
                    return;
                }
            }
        });
    }

    @FXML
    private void countryActionHandler(ActionEvent event) {
        try {
            cityDropdown.setItems(FXCollections.observableArrayList(
                    countryList.get(countryDropdown.getSelectionModel().getSelectedItem())));
            autoCompleteCityDropdown.refreshData();
            //cityDropdown.getSelectionModel().select(0);
        }
        catch (Exception e) {
            //This can occur from the search
        }
    }

    private void clearAllSelectionsFromBottomPane() {
        for(Node childPane : bottomGridPane.getChildren()) {
            childPane.getStyleClass().clear();
        }
    }

    @FXML
    public void bottomGridPaneElementSelectedHandler(MouseEvent mouseEvent) {
        try {
            TitanScene.getInstance().changePrimaryColor("");

            AnchorPane anchorPaneTarget = (AnchorPane)mouseEvent.getSource();
            if(anchorPaneTarget.getStyleClass().contains("clickedAnchorPane")) {
                //Uncheck and load current weather
                System.out.println("Load element 0");
                clearAllSelectionsFromBottomPane();
                return;
            }
            clearAllSelectionsFromBottomPane();
            anchorPaneTarget.getStyleClass().add("clickedAnchorPane");
            int weatherIndex = Integer.parseInt(anchorPaneTarget.getId().split("_")[1]);
            System.out.println("Load element " + weatherIndex);
        }
        catch (Exception e) {
        }
    }

    private void makeCityList() {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/ro/mta/se/lab/model/cityList.txt",
                    StandardCharsets.UTF_8);

            //Object obj = parser.parse(new FileReader("src/main/resources/ro/mta/se/lab/model/cityList.json"));
            String content = Files.readString(Paths.get("src/main/resources/ro/mta/se/lab/model/cityList.json"),
                    StandardCharsets.UTF_8);

            //myWriter.write("ID\tnm\tlat\tlon\tcountryCode\n");

            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONArray jsonArray = new JSONArray(content);
            for (Object o : jsonArray) {
                // A JSON array. JSONObject supports java.util.List interface.
                JSONObject current = (JSONObject) o;
                myWriter.write(current.get("id") + "\t" + current.get("name") + "\t");

                JSONObject coordList = current.getJSONObject("coord");
                myWriter.write(coordList.get("lat") + "\t" + coordList.get("lon") + "\t");
                myWriter.write(current.get("country") + "\n");
            }
            myWriter.close();
            System.out.println("Done!\n\n");
        } catch (Exception e) {
            TitanLogger.getInstance().write(e.getMessage(), 2, 1);
        }
    }


}
