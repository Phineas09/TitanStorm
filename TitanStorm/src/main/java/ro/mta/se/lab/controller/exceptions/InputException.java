package ro.mta.se.lab.controller.exceptions;

public class InputException extends _TitanException {
    /**
     * Constructor for InputException
     *
     * @param message custom error message
     */
    public InputException(String message) {
        super("InputException", message);
    }
}
