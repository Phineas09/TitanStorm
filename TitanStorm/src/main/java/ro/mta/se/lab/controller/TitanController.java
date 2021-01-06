package ro.mta.se.lab.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.view.TitanLogger;

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

    @FXML
    private ComboBox<City> cityDropdown;

    public TitanController() {
    }

    public TitanController(HashMap<String, ArrayList<City>> countryList) {
        this.countryList = countryList;
    }

    @FXML
    private void initialize() {

        Set<String> sortedKeys = new TreeSet<String>(countryList.keySet());
        countryDropdown.setItems(FXCollections.observableArrayList(sortedKeys));
        countryDropdown.getSelectionModel().select(0); //Auto select the first

        cityDropdown.setItems(FXCollections.observableArrayList(countryList.get(countryDropdown.getSelectionModel().getSelectedItem())));

        cityDropdown.setConverter(new StringConverter<>() {

            @Override
            public String toString(City object) {
                try {
                    return object.getName();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public City fromString(String string) {
                try {
                    return cityDropdown.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                } catch (Exception e) {
                    return null;
                }
            }
        });
        cityDropdown.getSelectionModel().select(0);

        //make request and write rez

    }

    @FXML
    private void cityActionHandler(ActionEvent event) {
        System.out.println(cityDropdown.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void countryActionHandler(ActionEvent event) {
        cityDropdown.setItems(FXCollections.observableArrayList(countryList.get(countryDropdown.getSelectionModel().getSelectedItem())));
        cityDropdown.getSelectionModel().select(0);
    }


    private void makeCityList() {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/ro/mta/se/lab/model/cityList.txt", StandardCharsets.UTF_8);

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
