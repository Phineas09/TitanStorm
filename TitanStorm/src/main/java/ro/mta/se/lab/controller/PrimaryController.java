package ro.mta.se.lab.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import org.json.JSONObject;
import ro.mta.se.lab.Main;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        Main.setRoot("secondary");
        JSONObject test = new JSONObject();
    }
}
