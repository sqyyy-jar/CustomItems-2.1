package dev.sqyyy.customitems.ii.exceptions;

public class AlreadyRegisteredError extends Exception {
    public AlreadyRegisteredError(String cause) {
        super("This object is already registered: " + cause);
    }
}
