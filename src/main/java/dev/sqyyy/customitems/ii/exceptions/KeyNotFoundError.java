package dev.sqyyy.customitems.ii.exceptions;

public class KeyNotFoundError extends Exception {
    public KeyNotFoundError(String key) {
        super("Could not find key: " + key);
    }
}
