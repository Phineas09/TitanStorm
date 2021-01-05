module ro.mta.se.lab {
    requires javafx.controls;
    requires javafx.fxml;

    opens ro.mta.se.lab to javafx.fxml;
    opens ro.mta.se.lab.controller to javafx.fxml;
    exports ro.mta.se.lab;
}
