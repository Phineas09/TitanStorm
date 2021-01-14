package ro.mta.se.lab.controller.exceptions;

/**
 * Class for the input exceptions inside the project
 */
public class HttpException extends _TitanException {
    /**
     * Constructor for InputException
     * @param message custom error message
     */
    public HttpException(String message) {
        super("HttpException", message);
    }
}

