package ro.mta.se.lab.view;

import ro.mta.se.lab.controller.exceptions.InputException;
import ro.mta.se.lab.controller.exceptions.TitanException;
import ro.mta.se.lab.controller.exceptions._TitanException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logger singleton class for the TitanStorm program
 */
public class TitanLogger {

    private static TitanLogger instance = null;  // singleton object
    private FileWriter outputFile;       // the writer who will do the job done writing output

    private TitanLogger() { this.outputFile = null; }

    /**
     * Public function to get an instance of the logger
     * @return instance of the logger
     */
    public synchronized static TitanLogger getInstance() {
        if (instance == null)
            instance = new TitanLogger();
        return instance;
    }

    /**
     * Will change or set the target outputFile.
     * @param filePath path to the target file
     * @throws TitanException if the path is invalid and the file can not be created or appended.
     */
    public synchronized void setOutputFile(String filePath) throws TitanException {
        try {
            if (filePath == null) {
                throw new InputException("Invalid filepath entered!");
            } else {
                //Close the old file if existent
                if(this.outputFile != null)
                    this.outputFile.close();
                File out = new File(filePath);
                out.createNewFile();
                outputFile = new FileWriter(out, StandardCharsets.UTF_8, true); //Utf 8
            }
        }
        catch(_TitanException | IOException err) {
            throw new InputException("Invalid filepath entered!");
        }
    }

    /**
     * Will write to file and console the given message with timestamp and log level
     * @param message message to write
     * @param stdout 0 for console, 1 for file and 2 for both
     * @param logLevel 1 for error, 2 for warning and 3 for info type of message.
     * @see #write(String, int)
     */
    public synchronized void write(String message, int stdout, int logLevel) {
        try {
            int globalLogLeve = 3;

            if(logLevel > globalLogLeve) {
                return;
            }

            switch (logLevel) {
                case 1:
                    message = "Error: " + message;
                    break;
                case 2:
                    message = "Warning: " + message;
                    break;
                case 3:
                    message = "Info: " + message;
                    break;
            }
            this.write(message, stdout);
        }
        catch (Exception crawlerException) {
            crawlerException.getMessage();
        }
    }

    /**
     * Will write to file and console the given message with timestamp
     * @param message message to write
     * @param stdout 0 for console, 1 for file and 2 for both
     */
    public synchronized void write(String message, int stdout){
        try {
            String timeStamp = new SimpleDateFormat("[ dd.MM.yyyy - HH.mm.ss ] ").format(new Date());
            message = timeStamp + message;

            switch (stdout) {
                case 0:
                    System.out.println(message);
                    break;
                case 1:
                    outputFile.write(message + "\n");
                    break;
                case 2:
                    System.out.println(message);
                    if(outputFile != null) {
                        outputFile.write(message + "\n");
                        outputFile.flush();
                    }
                    break;
                default:
                    throw new InputException("Unknown stdout provided!");
            }
        }
        catch(Exception err) {
            err.getMessage();
        }
    }
}
