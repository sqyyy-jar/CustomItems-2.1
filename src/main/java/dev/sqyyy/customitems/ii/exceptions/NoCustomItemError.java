package dev.sqyyy.customitems.ii.exceptions;

public class NoCustomItemError extends Exception {
    public NoCustomItemError(String materialName) {
        super("This is not a custom itemstack: " + materialName);
    }
}
