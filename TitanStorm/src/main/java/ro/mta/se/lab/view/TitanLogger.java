package ro.mta.se.lab.view;

import ro.mta.se.lab.controller.exceptions.InputException;
import ro.mta.se.lab.controller.exceptions.TitanException;
import ro.mta.se.lab.controller.exceptions._TitanException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TitanLogger {

    private static TitanLogger instance = null;  // singleton object
    private FileWriter outputFile;       // the writer who will do the job done writing output

    private TitanLogger() { this.outputFile = null; }

    public synchronized static TitanLogger getInstance() {
        if (instance == null)
            instance = new TitanLogger();
        return instance;
    }

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
                outputFile = new FileWriter(out, true);
            }
        }
        catch(_TitanException | IOException err) {
            throw new InputException("Invalid filepath entered!");
        }
    }

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
