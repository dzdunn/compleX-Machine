package uk.ac.uwe.complexmachine;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-6.0
 */
public class CompleXException extends Exception {
    /**
     * Creates a CompleXException from a message and another exception.
     * @param message the message to include in the exception
     * @param exception the exception to add to the stacktrace
     */
    public CompleXException(String message, Exception exception) {
        super(message, exception);
    }
}
