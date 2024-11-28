package hr.java.restaurant.exception;

public class EntityAlreadyInsertedException extends Exception {
    public EntityAlreadyInsertedException(String message) {
        super(message);
    }
}
