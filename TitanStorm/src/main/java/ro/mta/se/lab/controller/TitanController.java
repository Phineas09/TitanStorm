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
import ro.mta.se.lab.controller.exceptions.TitanException;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.WeatherModel;
import ro.mta.se.lab.view.TitanLogger;
import ro.mta.se.lab.view.TitanScene;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class TitanController {

    @FXML
    private ComboBox<String> countryDropdown;

    @FXML
    private ComboBox<City> cityDropdown;

    @FXML
    private ImageView currentWeatherIcon;

    @FXML
    private GridPane bottomGridPane;

    private HashMap<String, ArrayList<City>> countryList;
    private AutoCompleteComboBox<String> autoCompleteCityDropdown;
    private AutoCompleteComboBox<String> autoCompleteCountryDropdown;
    private WeatherProvider weatherProvider;

    public TitanController() {
    }

    public TitanController(HashMap<String, ArrayList<City>> countryList) {
        this.countryList = countryList;
        weatherProvider = new WeatherProvider();
    }

    @FXML
    private void initialize() {
        Set<String> sortedKeys = new TreeSet<>(countryList.keySet());
        countryDropdown.setItems(FXCollections.observableArrayList(sortedKeys));

        autoCompleteCountryDropdown = new AutoCompleteComboBox<>(countryDropdown);
        autoCompleteCityDropdown = new AutoCompleteComboBox<>(cityDropdown);
        threadDispatcherGetCurrentLocationForecast();
    }

    @FXML
    private void refreshCurrentForecastHandler(MouseEvent mouseEvent) {
        try {
            threadDispatcherSetForecast(cityDropdown.getSelectionModel().getSelectedItem());
            TitanLogger.getInstance().write("Refreshing forecast!", 2, 3);
        }
        catch (Exception ex) {
            System.out.println(ex.getCause());
        }
    }

    @FXML
    private void getCurrentLocationForecastHandler(MouseEvent mouseEvent) {
        threadDispatcherGetCurrentLocationForecast();
    }

    private void threadDispatcherGetCurrentLocationForecast() {
        TitanThread.runNewThread(() -> {
            if (!Thread.interrupted()) {
                try {
                    TitanLogger.getInstance().write("Finding location and loading forecast!", 2, 3);
                    //Make request to find the ip address

                    WeatherRequest requestMaker = new WeatherRequest();
                    String currentCityJSON = requestMaker.makeHttpRequest("https://geo.ipify.org/api/v1?apiKey=" +
                            requestMaker.getGeoApiKey());

                    //Create city object

                    JSONObject jsonObject = (new JSONObject(currentCityJSON)).getJSONObject("location");
                    City currentCity = new City("", jsonObject.getString("city"),
                            jsonObject.get("lat").toString(), jsonObject.get("lng").toString(),
                            jsonObject.getString("country"));

                Platform.runLater(() -> {
                    countryDropdown.getSelectionModel().select(currentCity.getCountryCode());
                    cityDropdown.getSelectionModel().select(currentCity);
                    //Event will trigger and wil auto load the currentCity, we just needed to find it!
                });
                } catch (TitanException titanException) {
                    titanException.getMessage();
                } catch (Exception e) {
                    TitanLogger.getInstance().write(e.getMessage(), 2, 1);
                }
            }
        });
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
        TitanThread.runNewThread(() -> {
            if (!Thread.interrupted()) {
                List<WeatherModel> weatherList =  weatherProvider.getWeekWeather(city);
                //Change background images and other stuff

                Platform.runLater(() -> {
                    TitanScene.getInstance().makeGridPanesVisible(); //Only executed once
                    clearAllSelectionsFromBottomPane();
                    loadMainWeatherForecast(weatherList.get(0));
                    loadSideWeatherForecast(weatherList);
                });
            }
        });
    }

    private void loadSideWeatherForecast(List<WeatherModel> weatherModelList) {
        //We start from 1 to < 6
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DecimalFormat df = new DecimalFormat("0.0");

        for (Node child : bottomGridPane.getChildren()) {
            AnchorPane anchorPane = (AnchorPane)child;
            int weatherIndex = Integer.parseInt(anchorPane.getId().split("_")[1]);
            WeatherModel currentWeather = weatherModelList.get(weatherIndex);

            File file = new File("src/main/resources/ro/mta/se/lab/icons/" + currentWeather.getWeatherIcon() + ".png");
            Image image = new Image(file.toURI().toString());
            //Get the icon
            ((ImageView)anchorPane.lookup("#bottomIcon_" + weatherIndex)).setImage(image);
            ((Label)anchorPane.lookup("#bottomDate_" + weatherIndex)).setText(dateFormat.format(currentWeather.getTimeNow()));
            ((Label)anchorPane.lookup("#bottomDescription_" + weatherIndex)).setText(currentWeather.getWeatherDescription());
            ((Label)anchorPane.lookup("#bottomMin_" + weatherIndex)).setText(df.format(Double.parseDouble(currentWeather.getMinTemp())) + "\u2103");
            ((Label)anchorPane.lookup("#bottomMax_" + weatherIndex)).setText(df.format(Double.parseDouble(currentWeather.getMaxTemp())) + "\u2103");

        }

    }

    private void loadMainWeatherForecast(WeatherModel weatherModel) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        DateFormat nowFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        //String strDate = dateFormat.format(date);

        File file = new File("src/main/resources/ro/mta/se/lab/icons/" + weatherModel.getWeatherIcon() + ".png");
        Image image = new Image(file.toURI().toString());
        currentWeatherIcon.setImage(image);

        DecimalFormat df = new DecimalFormat("0.0");


        ((Label)currentWeatherIcon.getParent().lookup("#weatherDescriptionCurrentDate")).setText(nowFormat.format(weatherModel.getTimeNow()));

        ((Label)currentWeatherIcon.getParent().lookup("#currentDegreesCLabel")).setText(df.format(Double.parseDouble(weatherModel.getCurrentTempC())) + "\u2103");
        ((Label)currentWeatherIcon.getParent().lookup("#currentDegreesFLabel")).setText(weatherModel.convertToF(weatherModel.getCurrentTempC()) + "\u2109");
        ((Label)currentWeatherIcon.getParent().lookup("#weatherDescriptionLabel")).setText(weatherModel.getWeatherDescription());
        ((Label)currentWeatherIcon.getParent().lookup("#pressureLabel")).setText(weatherModel.getPressure() + " hPa");
        ((Label)currentWeatherIcon.getParent().lookup("#cloudsLabel")).setText(weatherModel.getClouds() + " %");

        ((Label)currentWeatherIcon.getParent().lookup("#sunsetLabel")).setText(dateFormat.format(weatherModel.getSunSet()));
        ((Label)currentWeatherIcon.getParent().lookup("#sunriseLabel")).setText(dateFormat.format(weatherModel.getSunRise()));

        ((Label)currentWeatherIcon.getParent().lookup("#humidityLabel")).setText(weatherModel.getHumidity() + " %");
        ((Label)currentWeatherIcon.getParent().lookup("#uvIndexLabel")).setText(weatherModel.getUvIndex());

        ((Label)currentWeatherIcon.getParent().lookup("#windSpeedLabel")).setText(weatherModel.getWindSpeed() + " km/h");
        ((Label)currentWeatherIcon.getParent().lookup("#windDirectionLabel")).setText(weatherModel.getWindDeg() + " deg");

        //Change scene background and colors
        TitanScene.getInstance().changeBackgroundAndColorScheme(weatherModel);
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
    private void bottomGridPaneElementSelectedHandler(MouseEvent mouseEvent) {
        try {
            AnchorPane anchorPaneTarget = (AnchorPane)mouseEvent.getSource();
            if(anchorPaneTarget.getStyleClass().contains("clickedAnchorPane")) {
                //Uncheck and load current weather
                loadMainWeatherForecast(weatherProvider.getForecastList().get(0));
                clearAllSelectionsFromBottomPane();
                return;
            }
            clearAllSelectionsFromBottomPane();
            anchorPaneTarget.getStyleClass().add("clickedAnchorPane");
            int weatherIndex = Integer.parseInt(anchorPaneTarget.getId().split("_")[1]);
            loadMainWeatherForecast(weatherProvider.getForecastList().get(weatherIndex));

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
