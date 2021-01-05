package ro.mta.se.lab.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import ro.mta.se.lab.Main;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        Main.setRoot("secondary");
    }
}
