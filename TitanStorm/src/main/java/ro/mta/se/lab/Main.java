package ro.mta.se.lab;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.exceptions.TitanException;
import ro.mta.se.lab.view.TitanLogger;
import ro.mta.se.lab.view.TitanScene;

/**
 * Main class of the TitanStorm WeatherApp
 */
public class Main extends Application {

    /**
     * Default logger pathFile given by argument or the default one to be used | -logFile
     */
    private static String loggerFilePath = "src/main/resources/ro/mta/se/lab/logFile.txt";
    /**
     * InputCityFilePath given by argument or the default one to be used | -inFile
     */
    private static String inputCityFilePath = "src/main/resources/ro/mta/se/lab/model/cityList.txt";

    @Override
    public void start(Stage stage) {
        //Instantiate the TitanScene static class
        TitanScene.getInstance(stage, inputCityFilePath);
    }

    /**
     * Main function
     * @param args arguments given to the program
     *  <p><b>-inFile for the city file list and -logFile for the log file</b></p>
     */
    public static void main(String[] args) {
        try {
            //Parse arguments
            parseArguments(args);
            //Configure Logger
            TitanLogger.getInstance().setOutputFile(loggerFilePath);
            //Configure titanScene
            launch();
        }
        catch (TitanException exception) {
            exception.getMessage();
        }
    }

    /**
     * Parse arguments -inFile for the city file list and -logFile for the log file
     * @param args arguments given to the program
     */
    private static void parseArguments(String[] args) {
        if (args.length != 0) {
            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equalsIgnoreCase("-inFile")) {
                    inputCityFilePath = args[i + 1];
                }
                if (args[i].equalsIgnoreCase("-logFile")) {
                    loggerFilePath = args[i + 1];
                }
            }
        }
    }
}
