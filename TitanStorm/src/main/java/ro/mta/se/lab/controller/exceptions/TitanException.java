package ro.mta.se.lab.controller.exceptions;

/**
 * Public base abstract class for all the exceptions that can occur inside
 * the project TitanStorm
 */
public abstract class TitanException extends Exception {

    /**
     * Abstract method to be overridden by the inherited class
     * @return String formatted exception
     */
    public abstract String getMessage();
}
