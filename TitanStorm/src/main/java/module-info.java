module ro.mta.se.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens ro.mta.se.lab.controller to javafx.fxml;
    exports ro.mta.se.lab;
}
