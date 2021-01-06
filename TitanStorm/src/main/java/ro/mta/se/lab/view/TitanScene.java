package ro.mta.se.lab.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.Main;
import ro.mta.se.lab.controller.TitanController;

import java.io.IOException;

public class TitanScene {

    private static TitanScene instance = null;
    private static Scene scene = null;
    private static String ScenePath = "view/TitanScene";


    private TitanScene(String scenePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(scenePath + ".fxml"));
            fxmlLoader.setController(new TitanController());
            scene = new Scene(fxmlLoader.load());
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static TitanScene getInstance() {
        if(instance == null) {
            instance = new TitanScene(ScenePath);
        }
        return instance;
    }

    public static TitanScene getInstance(Stage stage) {
        if(instance == null) {
            instance = new TitanScene(ScenePath);
        }
        stage.setScene(scene);
        stage.show();
        return instance;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }




}
