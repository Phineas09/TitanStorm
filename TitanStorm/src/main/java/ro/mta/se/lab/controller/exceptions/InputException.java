package ro.mta.se.lab.controller.exceptions;

/**
 * Class for the input exceptions inside the project
 */
public class InputException extends _TitanException {
    /**
     * Constructor for InputException
     * @param message custom error message
     */
    public InputException(String message) {
        super("InputException", message);
    }
}
