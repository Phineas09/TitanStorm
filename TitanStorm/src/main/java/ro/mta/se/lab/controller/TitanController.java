package ro.mta.se.lab.controller;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import org.json.JSONArray;
import org.json.JSONObject;
import ro.mta.se.lab.model.City;


import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class TitanController {

    private ObservableMap<String, ArrayList<City>> countryList;

    @FXML
    private Label countryLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private ComboBox<City> countryDropdown;

    @FXML
    private ComboBox<City> cityDropdown;

    public TitanController() {

    }

    @FXML
    private void initialize() {

        City week_days[] = {
                new City(1, "Moara Vlasiei", "10", "10", "RO"),
                new City(2, "Balotesti", "12", "11", "RO")
        };

        cityDropdown.setItems(FXCollections.observableArrayList(week_days));
        //Set converter to get names
        cityDropdown.setConverter(new StringConverter<>() {
            @Override
            public String toString(City object) {
                return object.getName();
            }

            @Override
            public City fromString(String string) {
                return cityDropdown.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
        cityDropdown.getSelectionModel().select(0);

    }

    private void makeCityList() {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/ro/mta/se/lab/model/cityList.txt", StandardCharsets.UTF_8);

            //Object obj = parser.parse(new FileReader("src/main/resources/ro/mta/se/lab/model/cityList.json"));
            String content = Files.readString(Paths.get("src/main/resources/ro/mta/se/lab/model/cityList.json"),
                    StandardCharsets.UTF_8);
            myWriter.write("ID\tnm\tlat\tlon\tcountryCode\n");
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
            e.printStackTrace();
        }
    }

}
