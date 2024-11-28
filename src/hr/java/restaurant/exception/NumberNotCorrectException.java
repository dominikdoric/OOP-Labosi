package hr.java.restaurant.exception;

/**
 * Exception which is thrown when number is 0 or is negative.
 */
public class NumberNotCorrectException extends Exception {
    public NumberNotCorrectException(String message) {
        super(message);
    }
}
