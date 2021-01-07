package ro.mta.se.lab.controller.exceptions;

public class HttpException extends _TitanException {
    /**
     * Constructor for InputException
     *
     * @param message custom error message
     */
    public HttpException(String message) {
        super("HttpException", message);
    }
}

