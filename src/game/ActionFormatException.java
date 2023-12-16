package game;

/**
 * An exception that indicates that an action
 * is formatted incorrectly.
 */
public class ActionFormatException extends Exception {

    public ActionFormatException() {
        super();
    }

    public ActionFormatException(String message) {
        super(message);
    }
}
