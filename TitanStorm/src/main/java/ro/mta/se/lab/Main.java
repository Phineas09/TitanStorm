package ro.mta.se.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.exceptions.TitanException;
import ro.mta.se.lab.view.TitanLogger;
import ro.mta.se.lab.view.TitanScene;

import java.io.IOException;

public class Main extends Application {

    private static TitanScene titanScene;

    @Override
    public void start(Stage stage) throws TitanException {
        try {
            //Configure Logger
            TitanLogger.getInstance().setOutputFile("src/main/resources/ro/mta/se/lab/logFile.txt");
            titanScene = TitanScene.getInstance(stage);
        }
        catch (TitanException exception) {
            exception.getMessage();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        //scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
