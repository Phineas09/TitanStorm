package ro.mta.se.lab.controller.exceptions;


import ro.mta.se.lab.view.TitanLogger;

public class _TitanException extends TitanException {

    /** Message string, contains the corresponding error message */
    private String message;
    /** ExceptionType contains the type of the thrown error */
    private String exceptionType;

    /**
     * Constructor for _CrawlerException
     *
     * @param exceptionType exceptionType of the derived error
     * @param message custom error message
     */
    public _TitanException(String exceptionType, String message) {
        this.message = message;
        this.exceptionType = exceptionType;
    }

    /**
     * Returns a custom message for the current error "exceptionType : message"
     * @return String which represents the error type and error message
     */
    @Override
    public String getMessage() {
        String messageString = "Exception -> " + exceptionType + " : " + message;
        TitanLogger.getInstance().write(messageString, 2, 1); //Always show as errors
        return messageString;
    }
}
